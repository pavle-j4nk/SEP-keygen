package rs.ac.uns.ftn.sep.keygen.service.certificate;

import rs.ac.uns.ftn.sep.keygen.service.Subject;

import java.security.cert.X509Certificate;

public interface CertificateService {

    X509Certificate generateCertificate(Subject subject);

}
