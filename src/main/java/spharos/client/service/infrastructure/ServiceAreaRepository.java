package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.ServiceArea;
import java.util.List;

public interface ServiceAreaRepository extends JpaRepository<ServiceArea,Long> {

    List<Long> findServiceIdByAreaCodeIn(int areaCode);

}
