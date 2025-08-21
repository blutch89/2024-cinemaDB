package ch.blutch.cinemadb.business.impl.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.blutch.cinemadb.business.contract.manager.MovieManager;
import ch.blutch.cinemadb.consumer.contract.dao.db.MyMovieDbDao;
import ch.blutch.cinemadb.consumer.contract.dao.ws.MovieWsDao;
import ch.blutch.cinemadb.model.entities.cinemaelement.Credits;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.cinemaelement.my.MyMovie;
import ch.blutch.cinemadb.model.enums.LoadType;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;

@Transactional
@Service
@Qualifier("movieManager")
public class MovieManagerImpl implements MovieManager {

	@Autowired
	@Qualifier("movieWsDao")
	private MovieWsDao movieWsDao;
	
	@Autowired
	@Qualifier("myMovieDbDao")
	private MyMovieDbDao myMovieDbDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Movie> searchMovies(String query) throws Exception {
		// TODO Charger tous les genres car ça n'a pas de sens de n'en charger qu'un nombre réduit
		// - Voir le commentaire dans MovieWsDaoImpl.search() pour savoir comment intégrer les genres

		List<Movie> movies = movieWsDao.search(query);
		for (Movie movie : movies) {
			Credits credits = movieWsDao.getCredits(movie.getIdApi());
			credits.keepOnlyMainActors();
			movie.setCredits(credits);
		}

		return movies;
	}

	@Transactional(readOnly = true)
	@Override
	public Movie getMovie(int id) throws Cinemadb404Exception {
		return this.getMovie(id, LoadType.FULL);
	}

	@Transactional(readOnly = true)
	@Override
	public Movie getMovie(int id, LoadType loadType) throws Cinemadb404Exception {
		// TODO Charger tous les genres car ça n'a pas de sens de n'en charger qu'un nombre réduit
		// - Voir le commentaire dans MovieWsDaoImpl.get() pour savoir comment intégrer les genres (ils le sont déjà normalement)

		Movie movie = movieWsDao.get(id);

		// Si le LoadType == MEDIUM ou FULL, charge les crédits du film
		if (loadType != LoadType.MINIMUM) {
			Credits credits = movieWsDao.getCredits(id);
			
			// Si le LoadType == MEDIUM, ne garde que les 3 acteurs les plus importants (sinon il peut y en avoir 20 et ça surchargera les résultats)
			if (loadType == LoadType.MEDIUM) {
				credits.keepOnlyMainActors();
			}

			movie.setCredits(credits);
		}

		return movie;
	}

	@Transactional(readOnly = true)
	@Override
	public List<MyMovie> getMyMovies() {
		// Récupération des MyMovie
		List<MyMovie> myMovies = new ArrayList<>();
		myMovieDbDao.findAll().forEach(myMovies::add);
		
		// Ajout de l'objet Movie
		try {
			for (MyMovie myMovie : myMovies) {
				Movie movie = getMovie(myMovie.getIdApi());
				myMovie.setMovie(movie);
			}
		} catch (Cinemadb404Exception e) {
			// Ne rien faire et laisse l'attribut "movie" vide.
		}
		
		// Tri par titre de films
		Collections.sort(myMovies);
		
		return myMovies;
	}

	@Transactional(readOnly = true)
	@Override
	public MyMovie getMyMovie(int id) throws Cinemadb404Exception {
		try {
			// Récupération de MyMovie
			MyMovie myMovie = myMovieDbDao.findById(id).orElseThrow();
			
			// Ajout de l'objet Movie
			try {
				Movie movie = getMovie(myMovie.getIdApi());
				myMovie.setMovie(movie);
			} catch (Cinemadb404Exception e) {
				// Ne rien faire et laisse l'attribut "movie" vide.
			}
			
			return myMovie;
		} catch (NoSuchElementException e) {
			throw new Cinemadb404Exception();
		}
	}

	@Override
	public MyMovie saveMyMovie(MyMovie myMovie) {
		myMovie = myMovieDbDao.save(myMovie);
		
		// Ajout de l'objet Movie
		try {
			Movie movie = getMovie(myMovie.getIdApi());
			myMovie.setMovie(movie);
		} catch (Cinemadb404Exception e) {
			// Ne rien faire et laisse l'attribut "movie" vide.
		}

		return myMovie;
	}

	@Override
	public void deleteMyMovie(int id) throws Cinemadb404Exception {
		try {
			myMovieDbDao.deleteById(id);
		} catch (EmptyResultDataAccessException e) {		// Pas sûr de cette exception
			throw new Cinemadb404Exception();
		}
	}

}
