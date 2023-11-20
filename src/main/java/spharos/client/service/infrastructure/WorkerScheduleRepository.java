package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.worker.WorkerSchedule;
import spharos.client.service.domain.worker.enumType.DayOfWeekType;
import java.util.Optional;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    Optional<WorkerSchedule> findByDayOfWeekAndWorkerId(DayOfWeekType dayOfWeek, Long workerId);
    Optional<WorkerSchedule> findByWorkerId(Long workerId);
//    Optional<LocalTime> findEndTimeByDayOfWeekAndWorkerId(DayOfWeek date,Long workerId)
}
