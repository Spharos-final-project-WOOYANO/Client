package spharos.client.worker.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.worker.domain.WorkerReservationHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkerReservationHistoryRepository extends JpaRepository<WorkerReservationHistory,Long> {

    List<WorkerReservationHistory> findByReservationDateAndWorkerId(LocalDate date, Long workerId);

    Optional<WorkerReservationHistory> findByReservationNum(String reservationNum);

    List<WorkerReservationHistory> findByWorkerIdAndReservationDate(Long workerId, LocalDate date);

}
