package rs.ac.uns.ftn.sep.keygen.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Getter
@With
@RequiredArgsConstructor
public class Issuer {
	private final X500Name name;
	private final PrivateKey privateKey;
	private final X509Certificate certificate;
}
