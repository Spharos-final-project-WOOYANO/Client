package spharos.client.clients.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.clients.domain.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(String email);
    Optional<Client> findByCeoNameAndClientRegistrationNumber(String ceoName, String registrationNumber);
    Optional<Client> findByClientIdAndClientRegistrationNumber(String email, String registrationNumber);

    // TODO 이메일 과 사업자 번호로만

}
