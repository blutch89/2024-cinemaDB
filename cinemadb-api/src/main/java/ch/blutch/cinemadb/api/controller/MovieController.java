package ch.blutch.cinemadb.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ch.blutch.cinemadb.business.contract.manager.MovieManager;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.cinemaelement.my.MyMovie;
import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;
import jakarta.validation.Valid;

@RestController
@ResponseBody
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	@Qualifier("movieManager")
	private MovieManager movieManager;

	@GetMapping("/search/{query}")
	public ResponseEntity<List<Movie>> searchMovies(@PathVariable String query) throws Cinemadb404Exception, Exception {
		List<Movie> movies = movieManager.searchMovies(query);
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable int id) {
		Movie movie = movieManager.getMovie(id);
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	
	
	@GetMapping("/my")
	public ResponseEntity<List<MyMovie>> getMyMovies() {
		List<MyMovie> myMovies = movieManager.getMyMovies();
		return new ResponseEntity<List<MyMovie>>(myMovies, HttpStatus.OK);
	}
	
	
	@GetMapping("/my/{id}")
	public ResponseEntity<MyMovie> getMyMovie(@PathVariable int id) {
		MyMovie myMovie = movieManager.getMyMovie(id);
		return new ResponseEntity<MyMovie>(myMovie, HttpStatus.OK);
	}
	
	
	@PostMapping("/my")
	public ResponseEntity<MyMovie> createMyMovie(@Valid @RequestBody MyMovie myMovie) {
		MyMovie myMovieSaved = movieManager.saveMyMovie(myMovie);
		
		URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(myMovieSaved.getId())
              .toUri();
		
		return ResponseEntity.created(location).body(myMovieSaved);
	}
	
	
	@PutMapping("/my/{id}")
	public ResponseEntity<MyMovie> updateMyMovie(@PathVariable("id") int id, @Valid @RequestBody MyMovie myMovie) {
		MyMovie currentMyMovie = movieManager.getMyMovie(id);
		currentMyMovie.setIdApi(myMovie.getIdApi());
		currentMyMovie.setMyNote(myMovie.getMyNote());
		currentMyMovie.setViewDate(myMovie.getViewDate());
		
		MyMovie savedMyMovie = movieManager.saveMyMovie(currentMyMovie);
		
		return new ResponseEntity<MyMovie>(savedMyMovie, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/my/{id}")
	public ResponseEntity deleteMyMovie(@PathVariable("id") int id) {
		movieManager.deleteMyMovie(id);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
