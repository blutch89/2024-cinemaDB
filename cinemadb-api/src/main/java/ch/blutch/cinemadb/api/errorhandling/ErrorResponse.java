package ch.blutch.cinemadb.api.errorhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorResponse {

	private final int status;
	private final String message;
	private List<ValidationError> errors;

	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void addValidationError(String field, String message) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<>();
		}
		errors.add(new ValidationError(field, message));
	}
	
	private static class ValidationError {
		private final String field;
		private final String message;
		
		public ValidationError(String field, String message) {
			super();
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public String getMessage() {
			return message;
		}
	}
}
