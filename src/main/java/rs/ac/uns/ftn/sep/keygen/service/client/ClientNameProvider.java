package rs.ac.uns.ftn.sep.keygen.service.client;

import org.bouncycastle.asn1.x500.X500Name;
import rs.ac.uns.ftn.sep.keygen.bom.Client;

public interface ClientNameProvider {

    X500Name getName(Client client);

}
