package rs.ac.uns.ftn.sep.keygen.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyLoader;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyUtils;
import rs.ac.uns.ftn.sep.keygen.Resources;
import rs.ac.uns.ftn.sep.keygen.constants.Algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyUtilsEncodeDecodeTest {
    private static KeyPair keyPair;

    @BeforeAll
    static void init() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Algorithms.RSA);
        keyPair = keyPairGenerator.generateKeyPair();
    }

    @Test
    void encodeAndDecodePrivateKey() {
        PrivateKey privateKey = keyPair.getPrivate();
        String encoded = KeyUtils.encode(privateKey);
        PrivateKey decoded = KeyUtils.decodeToPrivate(encoded, Algorithms.RSA);

        assertEquals(privateKey, decoded);
    }

    @Test
    void encodeAndDecodePublicKey() {
        PublicKey publicKey = keyPair.getPublic();
        String encoded = KeyUtils.encode(publicKey);
        PublicKey decoded = KeyUtils.decodeToPublic(encoded, Algorithms.RSA);

        assertEquals(publicKey, decoded);
    }

    @Test
    void encodeAndDecodeCertificate() throws IOException {
        String encodedCertificate = Files.readString(ResourceUtils.getFile(Resources.certificate).toPath());
        encodedCertificate = KeyUtils.strip(encodedCertificate);

        X509Certificate certificate = KeyLoader.loadCertificate(Resources.certificate);

        assertEquals(encodedCertificate, KeyUtils.encode(certificate));
    }

}
