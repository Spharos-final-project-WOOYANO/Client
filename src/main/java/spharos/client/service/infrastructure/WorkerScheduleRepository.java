package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.worker.WorkerSchedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    Optional<LocalTime> findStartTimeByDayOfWeekAndWorkerId(DayOfWeek date, Long workerId);
    Optional<LocalTime> findEndTimeByDayOfWeekAndWorkerId(DayOfWeek date,Long workerId);
}
