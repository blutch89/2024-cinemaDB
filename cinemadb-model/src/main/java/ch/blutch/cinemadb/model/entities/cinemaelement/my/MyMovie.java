package ch.blutch.cinemadb.model.entities.cinemaelement.my;

import org.hibernate.annotations.DynamicUpdate;

import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "my_movie")
@DynamicUpdate
public class MyMovie extends MyCinemaElement implements Comparable<MyMovie> {
	
	@Transient
	private Movie movie;

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public int compareTo(MyMovie o) {
		if (movie == null) {
			return -1;
		}
		
		if (o.getMovie() == null) {
			return 1;
		}
		
		return movie.getTitle().compareToIgnoreCase(o.getMovie().getTitle());
	}
	
}
