package rs.ac.uns.ftn.sep.keygen.service.keygen;

import org.bouncycastle.asn1.x500.X500Name;
import rs.ac.uns.ftn.sep.commons.crypto.dto.*;
import rs.ac.uns.ftn.sep.keygen.service.Subject;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeygenService {

    SignedKeyDto generate(GenerateRequest request, X500Name name);

    SignedKeyDto generate(GenerateRequest request);

    SignedKeyDto generate(Subject subject, PrivateKey privateKey);

    CertificateDto sign(SignRequest signRequest);

    Subject createSubject(X500Name name, KeygenRequest request, PublicKey publicKey);
}
