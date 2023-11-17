package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.Services;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {
}
