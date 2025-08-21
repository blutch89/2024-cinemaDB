package ch.blutch.cinemadb.api.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import ch.blutch.cinemadb.model.exceptions.Cinemadb404Exception;
import io.netty.handler.codec.http.HttpHeaders;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	// Catch les exceptions lorsque les paramètres d'entrées des méthodes des contrôleurs ne sont pas valides
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		String message = "Erreur de validation survenue. Voir le champ 'errors' pour plus de détails";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), message);

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}
	

	// Catch les erreurs de type Cinemadb404Exception
	@ExceptionHandler(Cinemadb404Exception.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleItemNotFoundException(Cinemadb404Exception itemNotFoundException, WebRequest request) {
		return buildErrorResponse(itemNotFoundException, "L'élément demandé n'a pas été trouvé.", HttpStatus.NOT_FOUND, request);
	}
	

	// Catch toutes les autres exceptions
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllUncaughtException(RuntimeException exception, WebRequest request) {
		return buildErrorResponse(exception, "Une erreur inconnue s'est produite.", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	
	private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
		return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
	}
	

	private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);

		return ResponseEntity.status(httpStatus).body(errorResponse);
	}
}
