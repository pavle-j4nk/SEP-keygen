package rs.ac.uns.ftn.sep.keygen.service.keygen;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sep.commons.crypto.dto.*;
import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyUtils;
import rs.ac.uns.ftn.sep.keygen.bom.Client;
import rs.ac.uns.ftn.sep.keygen.constants.Algorithms;
import rs.ac.uns.ftn.sep.keygen.repository.ClientRepository;
import rs.ac.uns.ftn.sep.keygen.service.Issuer;
import rs.ac.uns.ftn.sep.keygen.service.Subject;
import rs.ac.uns.ftn.sep.keygen.service.certificate.CertificateService;
import rs.ac.uns.ftn.sep.keygen.service.client.ClientNameProvider;
import rs.ac.uns.ftn.sep.keygen.service.keypair.KeyPairGenerator;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeygenServiceImpl implements KeygenService {
    private final Set<String> DOMAINS = Set.of("localhost", "dev.local");

    private final ClientRepository clientRepository;
    private final CertificateService certificateService;
    private final KeyPairGenerator keyPairGenerator;
    private final ClientNameProvider clientNameProvider;
    private final Issuer issuer;

    @Override
    public SignedKeyDto generate(GenerateRequest request, X500Name name) {
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Subject subject = createSubject(name, request, keyPair.getPublic());

        return generate(subject, keyPair.getPrivate());
    }

    @Override
    public SignedKeyDto generate(GenerateRequest request) {
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Client client = clientRepository.getOneByToken(request.getToken());
        X500Name name = clientNameProvider.getName(client);
        Subject subject = createSubject(name, request, keyPair.getPublic());

        return generate(subject, keyPair.getPrivate());
    }

    @Override
    public SignedKeyDto generate(Subject subject, PrivateKey privateKey) {
        X509Certificate certificate = certificateService.generateCertificate(subject);
        String privateKeyEncoded = KeyUtils.encode(privateKey);
        String certificateEncoded = KeyUtils.encode(certificate);
        String caCertificateEncoded = KeyUtils.encode(issuer.getCertificate());

        return new SignedKeyDto(privateKeyEncoded, certificateEncoded, caCertificateEncoded);
    }

    @Override
    public CertificateDto sign(SignRequest signRequest) {
        Client client = clientRepository.getOneByToken(signRequest.getToken());
        X500Name name = clientNameProvider.getName(client);
        PublicKey publicKey = KeyUtils.decodeToPublic(signRequest.getPublicKey(), Algorithms.RSA);

        Subject subject = createSubject(name, signRequest, publicKey);
        X509Certificate certificate = certificateService.generateCertificate(subject);

        return new CertificateDto(KeyUtils.encode(certificate));
    }

    @Override
    public Subject createSubject(X500Name name, KeygenRequest request, PublicKey publicKey) {
        return Subject.builder()
                .name(name)
                .dateStart(getDateStart()).dateEnd(getDateEnd())
                .publicKey(publicKey)
                .ip(request.getIp())
                .domains(DOMAINS)
                .serial(System.currentTimeMillis())
                .build();
    }

    private Date getDateStart() {
        return new Date();
    }

    private Date getDateEnd() {
        LocalDate localDateEnd = LocalDate.now().plusYears(1);
        Instant instantEnd = localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instantEnd);
    }

}
