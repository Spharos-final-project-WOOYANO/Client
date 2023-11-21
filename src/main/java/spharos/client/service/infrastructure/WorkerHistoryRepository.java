package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.worker.WorkerReservationHistory;

import java.time.LocalDate;
import java.util.List;

public interface WorkerHistoryRepository extends JpaRepository<WorkerReservationHistory,Long> {

    List<WorkerReservationHistory> findByReservationDateAndWorkerId(LocalDate date,Long workerId);

}
