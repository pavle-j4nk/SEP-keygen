package rs.ac.uns.ftn.sep.keygen.configuration;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.sep.keygen.service.Issuer;

import java.security.cert.CertificateEncodingException;

@Configuration
@RequiredArgsConstructor
public class IssuerConfiguration {
    private final CaKeys caKeys;

    @Bean
    Issuer issuer() throws CertificateEncodingException {
        X500Name issuerName = new JcaX509CertificateHolder(caKeys.getCertificate()).getSubject();
        return new Issuer(issuerName, caKeys.getPrivateKey(), caKeys.getCertificate());
    }

}
