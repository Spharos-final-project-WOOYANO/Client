package spharos.client.reservation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.Services;

public interface ReservationServiceRepository extends JpaRepository<Services, Long> {

}
