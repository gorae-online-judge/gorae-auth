package ads02.auth.exception;


import lombok.Getter;

@Getter
public class GoraeException extends RuntimeException {
    private String message;

    public GoraeException(String message) {
        this.message = message;
    }
}
