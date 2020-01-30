package rs.ac.uns.ftn.sep.keygen.service.certificate;

public class SigningException extends RuntimeException {

    public SigningException(String message) {
        super(message);
    }

    public SigningException(String message, Throwable cause) {
        super(message, cause);
    }

    public SigningException(Throwable cause) {
        super(cause);
    }
}
