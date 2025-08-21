package ch.blutch.cinemadb.business.contract.manager;

import java.util.List;

import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.cinemaelement.my.MyMovie;
import ch.blutch.cinemadb.model.enums.LoadType;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;

public interface MovieManager {

	public List<Movie> searchMovies(String query) throws Cinemadb404Exception, Exception;
	public Movie getMovie(int id) throws Cinemadb404Exception;
	public Movie getMovie(int id, LoadType loadType) throws Cinemadb404Exception;
	public List<MyMovie> getMyMovies() throws Cinemadb404Exception;
	public MyMovie getMyMovie(int id) throws Cinemadb404Exception;
	public MyMovie saveMyMovie(MyMovie myMovie) throws Cinemadb404Exception;
	public void deleteMyMovie(int id) throws Cinemadb404Exception;
	
}
