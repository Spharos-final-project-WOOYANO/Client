package spharos.client.reservation.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.clients.domain.Client;

public interface ReservationClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c.id FROM Client c WHERE c.clientId = :email")
    Long findByClientId(@Param("email")String email);
    Optional<Client> findByClientRegistrationNumber(String registrationNumber);

}
