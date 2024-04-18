package my.ecommerce.presentation;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import my.ecommerce.utils.CustomException;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		logger.error("Unhandled exception : {}", e.getMessage(), e);

		ErrorResponse errorResponse = new ErrorResponse("INTERNAL_ERROR", e.getMessage());
		return ResponseEntity.status(500).body(errorResponse);
	}

	@ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
		logger.warn("Bad request : {}", e.getMessage(), e);

		ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", e.getMessage());
		return ResponseEntity.status(400).body(errorResponse);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
		logger.warn("Entity not found : {}", e.getMessage(), e);

		ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", e.getMessage());
		return ResponseEntity.status(404).body(errorResponse);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		switch (e.getLogLevel()) {
			case ERROR -> logger.error("Api exception : {}", e.getMessage(), e);
			case WARN -> logger.warn("Api exception : {}", e.getMessage(), e);
			case INFO -> logger.info("Api exception : {}", e.getMessage(), e);
		}

		ErrorResponse errorResponse = new ErrorResponse(e.getCode(), e.getMessage());
		return ResponseEntity.status(e.getStatus()).body(errorResponse);
	}
}
