package client;

public class AuthorizationException extends Exception {
    public AuthorizationException(String problem) {
        super(problem);
    }
}
