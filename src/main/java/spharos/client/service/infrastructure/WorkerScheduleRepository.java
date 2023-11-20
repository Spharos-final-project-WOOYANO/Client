package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.worker.WorkerSchedule;
import java.time.LocalTime;
import java.util.Optional;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    Optional<LocalTime> findStartTimeByWorkerId(Long workerId);
    Optional<LocalTime> findEndTimeByWorkerId(Long workerId);
}
