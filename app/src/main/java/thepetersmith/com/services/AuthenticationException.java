package thepetersmith.com.services;

/**
 * Created by smith on 2016-03-11.
 */
public class AuthenticationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }
}
