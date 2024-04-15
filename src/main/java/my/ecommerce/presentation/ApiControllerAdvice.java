package my.ecommerce.presentation;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import my.ecommerce.domain.product.exceptions.InsufficientStockException;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		ErrorResponse errorResponse = new ErrorResponse("INTERNAL_ERROR", e.getMessage());
		return ResponseEntity.status(500).body(errorResponse);
	}

	@ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", e.getMessage());
		return ResponseEntity.status(400).body(errorResponse);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", e.getMessage());
		return ResponseEntity.status(404).body(errorResponse);
	}

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException e) {
		ErrorResponse errorResponse = new ErrorResponse("INSUFFICIENT_STOCK", e.getMessage());
		return ResponseEntity.status(422).body(errorResponse);
	}
}
