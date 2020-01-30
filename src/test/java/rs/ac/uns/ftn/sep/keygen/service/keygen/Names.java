package rs.ac.uns.ftn.sep.keygen.service.keygen;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class Names {
    public static final X500Name clientName = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, "Client")
                .addRDN(BCStyle.O, "FTN")
                .addRDN(BCStyle.OU, "SEP")
                .addRDN(BCStyle.C, "SR")
                .addRDN(BCStyle.E, "client@mail.com")
                .addRDN(BCStyle.UID, "1")
                .build();

    public static final X500Name issuerName = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, "Issuer")
                .addRDN(BCStyle.O, "FTN")
                .addRDN(BCStyle.OU, "SEP")
                .addRDN(BCStyle.C, "SR")
                .addRDN(BCStyle.E, "issuer@mail.com")
                .addRDN(BCStyle.UID, "0")
                .build();
}
