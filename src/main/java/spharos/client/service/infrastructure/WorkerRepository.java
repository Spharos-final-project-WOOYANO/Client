package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.worker.Worker;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByServiceId(Long serviceId);
}
