package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.ServiceArea;
import spharos.client.service.domain.services.Services;

import java.util.List;

public interface ServiceAreaRepository extends JpaRepository<ServiceArea,Long> {

    List<Long> findServicesIdByAreaCode(int areaCode);
    List<ServiceArea> findByServices(Services services);

}
