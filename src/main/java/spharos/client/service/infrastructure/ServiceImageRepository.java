package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.ServiceImage;
import spharos.client.service.domain.services.Services;

import java.util.List;

public interface ServiceImageRepository extends JpaRepository<ServiceImage, Long> {

    List<ServiceImage> findByService(Services services);

}
