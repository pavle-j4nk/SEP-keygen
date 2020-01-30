package rs.ac.uns.ftn.sep.keygen.service.client;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sep.keygen.bom.Client;
import rs.ac.uns.ftn.sep.keygen.properties.KeygenProperties;

@Service
public class ClientNameProviderImpl implements ClientNameProvider {
    private final KeygenProperties keygenProperties;

    public ClientNameProviderImpl(KeygenProperties keygenProperties) {
        this.keygenProperties = keygenProperties;
    }

    @Override
    public X500Name getName(Client client) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, client.getCommonName())
                .addRDN(BCStyle.O, keygenProperties.getOrganization())
                .addRDN(BCStyle.OU, keygenProperties.getOrganizationalUnit())
                .addRDN(BCStyle.C, keygenProperties.getCountryCode())
                .addRDN(BCStyle.E, client.getEmail())
                .addRDN(BCStyle.UID, String.valueOf(System.currentTimeMillis()));

        return builder.build();
    }

}
