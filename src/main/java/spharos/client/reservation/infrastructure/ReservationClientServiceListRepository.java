package spharos.client.reservation.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;

public interface ReservationClientServiceListRepository extends JpaRepository<ClientServiceList, Long> {

    List<ClientServiceList> findByClient(Client client);

    @Query("SELECT c.services.id FROM ClientServiceList c WHERE c.client.id = :clientId")
    Long findByServicesId(@Param("clientId") Long clientId);





}
