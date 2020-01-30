package rs.ac.uns.ftn.sep.keygen.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import rs.ac.uns.ftn.sep.keygen.properties.KeygenProperties;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
@RequiredArgsConstructor
public class CaKeys {
    private final KeygenProperties keygenProperties;

    @Getter
    private PrivateKey privateKey;
    @Getter
    private PublicKey publicKey;
    @Getter
    private X509Certificate certificate;

    @PostConstruct
    public void init() throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("JKS");

        InputStream keystoreInputStream = new FileInputStream(ResourceUtils.getFile(keygenProperties.getKeyStore()));
        char[] keystorePassword = keygenProperties.getKeyStorePassword().toCharArray();
        keyStore.load(keystoreInputStream, keystorePassword);

        String keyPassword = keygenProperties.getCaKeyPassword();
        char[] passwordCharArray = keyPassword != null ? keyPassword.toCharArray() : null;

        this.privateKey = (PrivateKey) keyStore.getKey(keygenProperties.getCaKeyAlias(), passwordCharArray);
        this.certificate = (X509Certificate) keyStore.getCertificate(keygenProperties.getCaKeyAlias());
        this.publicKey = certificate.getPublicKey();
    }

}
