package ch.blutch.cinemadb.consumer.contract.dao.ws;

import java.util.List;
import java.util.Map;

import ch.blutch.cinemadb.model.entities.cinemaelement.Credits;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.persons.Person;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;

public interface MovieWsDao {

	public List<Movie> search(String query) throws Cinemadb404Exception, Exception;
	public Movie get(int id) throws Cinemadb404Exception;
	public Credits getCredits(int movieId) throws Cinemadb404Exception;
	
}
