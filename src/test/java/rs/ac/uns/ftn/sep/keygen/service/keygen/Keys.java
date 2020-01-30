package rs.ac.uns.ftn.sep.keygen.service.keygen;

import rs.ac.uns.ftn.sep.commons.crypto.utils.KeyLoader;
import rs.ac.uns.ftn.sep.keygen.Resources;

import java.security.PrivateKey;
import java.security.cert.Certificate;

class Keys {
    static final PrivateKey issuerPrivateKey = KeyLoader.loadPrivate(Resources.caPrivate);
    static final Certificate issuerCertificate = KeyLoader.loadCertificate(Resources.caCert);
}
