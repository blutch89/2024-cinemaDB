package ch.blutch.cinemadb.consumer.impl.dao.ws;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import ch.blutch.cinemadb.consumer.contract.dao.ws.MovieWsDao;
import ch.blutch.cinemadb.consumer.contract.dao.ws.factories.WsFactory;
import ch.blutch.cinemadb.model.entities.cinemaelement.Credits;
import ch.blutch.cinemadb.model.entities.cinemaelement.HasPlayed;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.cinemaelement.Role;
import ch.blutch.cinemadb.model.entities.persons.Person;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;
import reactor.core.publisher.Mono;

@Repository
@Qualifier("movieWsDao")
public class MovieWsDaoImpl extends AbstractWsDaoImpl implements MovieWsDao {

	@Autowired
	@Qualifier("wsFactory")
	private WsFactory wsFactory;
	
	@Override
	public List<Movie> search(String query) throws Exception {
		try {
			List<Movie> toReturn = new ArrayList<>();
			
			// Resquest API
			WebClient webClient = this.createWebClient();
			String uri = this.createUri("/search/movie", new String[] {"query=" + query});
			
			Mono<String> searchDatas = webClient.get()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class);
			
			// Conversion du JSON en Movie
			JSONObject result = new JSONObject(searchDatas.block());
			JSONArray moviesArr = result.getJSONArray("results");
			
			for (int i = 0; i < moviesArr.length(); i++) {
				JSONObject movieObj = moviesArr.getJSONObject(i);
				Movie movie = this.wsFactory.createMovie(movieObj);
				
				// TODO Charger les genres: pour chaque id de genre du film en cours, charger l'objet Genre en utilisant le futur GenreWsDao
				// TODO Ou alors, vu qu'avec l'uri search je ne reçois que l'ID du genre, créer ici un objet Genre seulement avec son id et l'attribuer à la Movie
				// - Ensuite, dans le Manager, je pourrais récupérer les ids de genre et appeler le futur GenreWsDao pour récupérer son libellé.

				toReturn.add(movie);
			}
			
			return toReturn;
		} catch (WebClientResponseException exception) {
			if (exception.getStatusCode() == HttpStatusCode.valueOf(404)) {
				throw new Cinemadb404Exception();
			} else {
				throw new RuntimeException();				
			}
		}
	}

	@Override
	public Movie get(int id) throws Cinemadb404Exception {
		try {
			Movie toReturn = null;
			
			// Resquest API
			WebClient webClient = this.createWebClient();
			String uri = this.createUri("/movie/" + id, new String[] {});
			
			Mono<String> getDatas = webClient.get()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class);
			
			// Conversion du JSON en Movie
			JSONObject result = new JSONObject(getDatas.block());
			toReturn = this.wsFactory.createMovie(result);

			// TODO Charger les genres: ils sont retournés avec leur libellé dans l'action /movie de TMDB. Donc juste créer/hydrater les genres et les appliquer à la vidéo

			return toReturn;
		} catch (WebClientResponseException exception) {
			if (exception.getStatusCode() == HttpStatusCode.valueOf(404)) {
				throw new Cinemadb404Exception();
			} else {
				throw new RuntimeException();			
			}
		}
	}

	@Override
	public Credits getCredits(int movieId) throws Cinemadb404Exception {
		try {
			Credits toReturn = new Credits();

			// Resquest API
			WebClient webClient = this.createWebClient();
			String uri = this.createUri("/movie/" + movieId + "/credits", new String[] {});
			
			Mono<String> creditsDatas = webClient.get()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class);
			
				
			// Récupération des acteurs
			JSONObject result = new JSONObject(creditsDatas.block());
			JSONArray cast = result.getJSONArray("cast");
			for (int i = 0; i < cast.length(); i++) {
				JSONObject creditObj = cast.getJSONObject(i);

				Person person = this.wsFactory.createPerson(creditObj);
				String typeCredit = creditObj.getString("known_for_department");

				// Si c'est un acteur
				if (typeCredit.equals("Acting")) {
					Role role = new Role(creditObj.getString("character"), creditObj.getInt("order"));

					HasPlayed actor = toReturn.getActorByIdApi(creditObj.getInt("id"));
					if (actor != null) {
						actor.addRole(role);
					} else {
						actor = new HasPlayed();
						actor.setActor(person);
						actor.addRole(role);
					}
					
					toReturn.addActor(actor);
				}
			}


			// Récupération de l'équipe
			JSONArray crew = result.getJSONArray("crew");
			for (int i = 0; i < crew.length(); i++) {
				JSONObject creditObj = crew.getJSONObject(i);

				Person person = this.wsFactory.createPerson(creditObj);
				String job = creditObj.has("job") && !creditObj.isNull("job") ? creditObj.getString("job") : "";

				// Si c'est un producteur
				if (job.equals("Producer")) {
					toReturn.addProducer(person);
				}
				// Si c'est un réalisateur
				else if (job.equals("Director")) {
					toReturn.addDirector(person);
				}
			}

			return toReturn;
		} catch (WebClientResponseException exception) {
			if (exception.getStatusCode() == HttpStatusCode.valueOf(404)) {
				throw new Cinemadb404Exception();
			} else {
				throw new RuntimeException();			
			}
		}
	}

}
