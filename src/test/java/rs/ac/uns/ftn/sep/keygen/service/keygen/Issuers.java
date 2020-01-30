package rs.ac.uns.ftn.sep.keygen.service.keygen;

import rs.ac.uns.ftn.sep.keygen.service.Issuer;

import java.security.cert.X509Certificate;

public class Issuers {
    public static final Issuer issuer = new Issuer(Names.issuerName, Keys.issuerPrivateKey, (X509Certificate) Keys.issuerCertificate);
}
