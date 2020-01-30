package rs.ac.uns.ftn.sep.keygen.service.certificate;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import rs.ac.uns.ftn.sep.keygen.service.Issuer;
import rs.ac.uns.ftn.sep.keygen.service.Subject;

import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CertificateServiceImpl implements CertificateService {
    private static final String SHA_256_WITH_RSA_ENCRYPTION = "SHA256WithRSAEncryption";
    private static final String BOUNCY_CASTLE = "BC";

    private final JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter().setProvider(BOUNCY_CASTLE);

    private final Issuer issuer;
    private final ContentSigner contentSigner;

    public CertificateServiceImpl(Issuer issuer) {
        this.issuer = issuer;

        try {
            contentSigner = new JcaContentSignerBuilder(SHA_256_WITH_RSA_ENCRYPTION)
                    .setProvider(BOUNCY_CASTLE)
                    .build(issuer.getPrivateKey());
        } catch (OperatorCreationException e) {
            throw new SigningException(e);
        }
    }

    @Override
    public X509Certificate generateCertificate(Subject subject) {
        X509CertificateHolder certHolder = createCertificateHolder(subject);

        try {
            return certConverter.getCertificate(certHolder);
        } catch (CertificateException e) {
            throw new SigningException(e);
        }
    }

    private X509CertificateHolder createCertificateHolder(Subject subject) {
        X509CertificateHolder certHolder;
        try {
            certHolder = new JcaX509v3CertificateBuilder(
                    issuer.getName(),
                    BigInteger.valueOf(subject.getSerial()),
                    subject.getDateStart(),
                    subject.getDateEnd(),
                    subject.getName(),
                    subject.getPublicKey())
                    .addExtension(Extension.subjectAlternativeName, true, getAlternativeNames(subject))
                    .build(contentSigner);
        } catch (CertIOException e) {
            throw new SigningException(e);
        }

        return certHolder;
    }

    private DERSequence getAlternativeNames(Subject subject) {
        List<ASN1Encodable> names = new ArrayList<>();

        Function<String, GeneralName> ipToName = ip -> new GeneralName(GeneralName.iPAddress, ip);
        Function<String, GeneralName> dnsToName = dns -> new GeneralName(GeneralName.dNSName, dns);

        names.addAll(subject.getIp().stream().map(ipToName).collect(Collectors.toList()));
        names.addAll(subject.getDomains().stream().map(dnsToName).collect(Collectors.toList()));

        return new DERSequence(names.toArray(new ASN1Encodable[0]));
    }

}
