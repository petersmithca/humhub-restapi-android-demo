package thepetersmith.com.services;

/**
 * Created by smith on 2016-03-11.
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;

    public ApiException(String message) {
        super(message);
    }

}
