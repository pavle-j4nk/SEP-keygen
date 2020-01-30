package rs.ac.uns.ftn.sep.keygen.service.keypair;

public class KeyGeneratorException extends RuntimeException {

    public KeyGeneratorException(String message) {
        super(message);
    }

    public KeyGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyGeneratorException(Throwable cause) {
        super(cause);
    }
}
