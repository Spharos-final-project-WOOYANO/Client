package spharos.client.worker.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.worker.domain.WorkerReservationHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkerReservationHistoryRepository extends JpaRepository<WorkerReservationHistory,Long> {

    List<WorkerReservationHistory> findByReservationDateAndWorkerId(LocalDate date, Long workerId);

    Optional<WorkerReservationHistory> findByReservationNum(String reservationNum);

    @Query("SELECT wrh FROM WorkerReservationHistory wrh JOIN wrh.worker w WHERE w.id = :workerId AND wrh.reservationDate = :date")
    List<WorkerReservationHistory> findByWorkerIdAndReservationDate(@Param("workerId") Long workerId, @Param("date") LocalDate date);

}
