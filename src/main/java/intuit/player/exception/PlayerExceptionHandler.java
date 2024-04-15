package intuit.player.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class PlayerExceptionHandler {

    // Handle PlayerNotFoundException for missing resources
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(PlayerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        return new ResponseEntity<>("No handler found for your request", HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce("", (subtotal, element) -> subtotal + element + "; ");
        return new ResponseEntity<>("Validation error: " + errors, HttpStatus.BAD_REQUEST);
    }

    // Handle general and unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
