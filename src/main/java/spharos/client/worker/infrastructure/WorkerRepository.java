package spharos.client.worker.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.worker.domain.Worker;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByServiceId(Long serviceId);
}
