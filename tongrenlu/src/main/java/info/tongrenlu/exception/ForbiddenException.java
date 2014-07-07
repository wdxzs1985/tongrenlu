package info.tongrenlu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ForbiddenException(final String message) {
        super(message);
    }
}
