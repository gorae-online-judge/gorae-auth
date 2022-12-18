package ads02.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(GoraeException.class)
    public ResponseEntity<String> jasomeExceptionHandler(final GoraeException exception){
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }
}
