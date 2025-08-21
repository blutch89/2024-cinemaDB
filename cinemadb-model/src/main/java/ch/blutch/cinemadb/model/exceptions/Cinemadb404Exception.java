package ch.blutch.cinemadb.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Cinemadb404Exception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Cinemadb404Exception() {
		super();
	}

	public Cinemadb404Exception(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
