package spharos.client.worker.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.services.Services;
import spharos.client.worker.domain.Worker;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByServiceId(Long serviceId);
    Optional<Worker> findById(Long workerId);
    List<Worker> findByService(Services services);
}
