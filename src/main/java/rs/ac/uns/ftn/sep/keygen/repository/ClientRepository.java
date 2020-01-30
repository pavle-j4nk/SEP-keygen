package rs.ac.uns.ftn.sep.keygen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.sep.keygen.bom.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client getOneByToken(String token);

}
