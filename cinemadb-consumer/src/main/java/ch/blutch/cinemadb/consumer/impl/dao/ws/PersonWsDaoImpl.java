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

import ch.blutch.cinemadb.consumer.contract.dao.ws.PersonWsDao;
import ch.blutch.cinemadb.consumer.contract.dao.ws.factories.WsFactory;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.persons.Person;
import ch.blutch.cinemadb.model.enums.LoadType;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;
import reactor.core.publisher.Mono;

@Repository
@Qualifier("personWsDao")
public class PersonWsDaoImpl extends AbstractWsDaoImpl implements PersonWsDao {

	@Autowired
	@Qualifier("wsFactory")
	private WsFactory wsFactory;

    @Override
    public List<Person> searchPersons(String query) {
        try {
            List<Person> toReturn = new ArrayList<>();

            // Resquest API
			WebClient webClient = this.createWebClient();
			String uri = this.createUri("/search/person", new String[] {"query=" + query});
			
			Mono<String> searchDatas = webClient.get()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class);
			
			// Conversion du JSON en Person
			JSONObject result = new JSONObject(searchDatas.block());
			JSONArray personsArr = result.getJSONArray("results");
			
			for (int i = 0; i < personsArr.length(); i++) {
				JSONObject personObj = personsArr.getJSONObject(i);
                Person person = this.wsFactory.createPerson(personObj);
				
				toReturn.add(person);
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
    public Person getPerson(int id) {
        return this.getPerson(id, LoadType.FULL);
    }

	@Override
	public Person getPerson(int id, LoadType loadType) {
		try {
			Person toReturn = null;

			// Resquest API
			WebClient webClient = this.createWebClient();
			String uri = this.createUri("/person/" + id, new String[] {});
			
			Mono<String> getDatas = webClient.get()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class);
			
			// Conversion du JSON en Movie
			JSONObject result = new JSONObject(getDatas.block());
			toReturn = this.wsFactory.createPerson(result);

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
