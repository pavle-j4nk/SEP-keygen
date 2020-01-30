package rs.ac.uns.ftn.sep.keygen;

public interface Resources {
    String privateKey = "classpath:private.pem";
    String certificate = "classpath:certificate.pem";
    String caPrivate = "classpath:ca.pem";
    String caCert = "classpath:ca.crt";
    String examplePublicKey = "classpath:example.pub";
}
