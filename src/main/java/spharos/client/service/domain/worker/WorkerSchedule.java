package spharos.client.service.domain.worker;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.service.domain.services.Services;
import spharos.client.service.domain.worker.enumType.DayOfWeekType;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "worker_schedule")
public class WorkerSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Services service;

    @Column(nullable = false, length = 20, name = "day_of_week")
    private DayOfWeekType dayOfWeek;

    @Column(nullable = false, length = 20, name = "start_time")
    private LocalTime startTime;

    @Column(nullable = false, length = 20, name = "last_time")
    private LocalTime lastTime;

    public WorkerSchedule(Services service,
                          DayOfWeekType dayOfWeek,
                          LocalTime startTime,
                          LocalTime lastTime) {
        this.service = service;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.lastTime = lastTime;
    }
    public static WorkerSchedule createWorkerSchedule(Services service,
                                                      DayOfWeekType dayOfWeek,
                                                      LocalTime startTime,
                                                      LocalTime lastTime) {
        return new WorkerSchedule(service, dayOfWeek, startTime, lastTime);
    }

}
