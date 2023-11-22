package spharos.client.clients.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;

import java.util.List;

public interface ClientServiceListRepository extends JpaRepository<ClientServiceList, Long> {

    List<ClientServiceList> findByClient(Client client);

    ClientServiceList findByServicesId(Long serviceId);

}
