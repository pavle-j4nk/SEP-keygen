package rs.ac.uns.ftn.sep.keygen.service.keygen;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.sep.commons.crypto.dto.CertificateDto;
import rs.ac.uns.ftn.sep.commons.crypto.dto.GenerateRequest;
import rs.ac.uns.ftn.sep.commons.crypto.dto.SignRequest;
import rs.ac.uns.ftn.sep.commons.crypto.dto.SignedKeyDto;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyUtils;
import rs.ac.uns.ftn.sep.keygen.constants.Algorithms;
import rs.ac.uns.ftn.sep.keygen.service.certificate.CertificateServiceImpl;
import rs.ac.uns.ftn.sep.keygen.service.keypair.KeyPairGeneratorImpl;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.function.Function;

class KeygenServiceImplTest {
    private static final String TOKEN = "foo";
    private static final String IP = "127.0.0.1";

    private static final GenerateRequest GENERATE_REQUEST = new GenerateRequest(TOKEN, Set.of(IP));
    private static final Function<String, SignRequest> SIGN_REQUEST = (pk) -> new SignRequest(pk, TOKEN, Set.of(IP));

    private static KeygenService keygenService;

    @BeforeAll
    static void init() {
        Security.addProvider(new BouncyCastleProvider());

        keygenService = new KeygenServiceImpl(
                null
                , new CertificateServiceImpl(Issuers.issuer)
                , new KeyPairGeneratorImpl()
                , (t) -> Names.clientName
                , Issuers.issuer);
    }

    @Test
    void generate() throws NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        SignedKeyDto generate = keygenService.generate(GENERATE_REQUEST);
        X509Certificate clientCert = KeyUtils.decodeToCertificate(generate.getClientCertificate());

        clientCert.verify(Keys.issuerCertificate.getPublicKey());
    }

    @Test
    void sign() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, InvalidKeyException, SignatureException {
        KeyPair keyPair = KeyPairGenerator.getInstance(Algorithms.RSA).generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        String encoded = KeyUtils.encode(publicKey);

        CertificateDto certificateDto = keygenService.sign(SIGN_REQUEST.apply(encoded));
        X509Certificate certificate = KeyUtils.decodeToCertificate(certificateDto.getCertificate());

        certificate.verify(Keys.issuerCertificate.getPublicKey());
    }

}
