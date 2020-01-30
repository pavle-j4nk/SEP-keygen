package rs.ac.uns.ftn.sep.keygen.configuration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.sep.keygen.service.Issuer;
import rs.ac.uns.ftn.sep.keygen.service.certificate.CertificateService;
import rs.ac.uns.ftn.sep.keygen.service.certificate.CertificateServiceImpl;

import java.security.Security;

@Configuration
public class CertificateServiceConfiguration {

    @Bean
    public CertificateService certificateService(Issuer issuer) {
        Security.addProvider(new BouncyCastleProvider());

        return new CertificateServiceImpl(issuer);
    }

}
