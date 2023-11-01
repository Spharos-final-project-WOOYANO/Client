package spharos.client.clients.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.clients.domain.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(String email);

}
