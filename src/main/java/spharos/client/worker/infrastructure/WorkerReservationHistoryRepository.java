package spharos.client.worker.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.worker.domain.WorkerReservationHistory;
import java.time.LocalDate;
import java.util.List;

public interface WorkerReservationHistoryRepository extends JpaRepository<WorkerReservationHistory,Long> {

    List<WorkerReservationHistory> findByReservationDateAndWorkerId(LocalDate date, Long workerId);

}
