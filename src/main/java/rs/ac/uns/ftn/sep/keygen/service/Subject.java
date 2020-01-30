package rs.ac.uns.ftn.sep.keygen.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@With
@Getter
@Builder
public class Subject {
    private final PublicKey publicKey;
    private final X500Name name;
    private final Set<String> ip;
    private final Set<String> domains;
    private final Long serial;
    private final Date dateStart;
    private final Date dateEnd;
}
