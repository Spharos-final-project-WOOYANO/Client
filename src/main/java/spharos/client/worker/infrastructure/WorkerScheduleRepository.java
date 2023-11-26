package spharos.client.worker.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.worker.domain.Worker;
import spharos.client.worker.domain.WorkerSchedule;
import spharos.client.worker.domain.enumType.DayOfWeekType;

import java.util.List;
import java.util.Optional;

public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    Optional<WorkerSchedule> findByDayOfWeekAndWorkerId(DayOfWeekType dayOfWeek, Long workerId);
    Optional<WorkerSchedule> findByWorkerId(Long workerId);
    List<WorkerSchedule> findByWorker(Worker worker);
}
