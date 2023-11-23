package spharos.client.worker.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.service.domain.services.Services;
import spharos.client.worker.domain.enumType.DayOfWeekType;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 20, name = "start_time")
    private LocalTime serviceStartTime;

    @Column(nullable = false, length = 20, name = "last_time")
    private LocalTime serviceFinishTime;

    public WorkerSchedule(Services service,
                            Worker worker,
                            DayOfWeekType dayOfWeek,
                            LocalTime serviceStartTime,
                            LocalTime serviceFinishTime) {
        this.service = service;
        this.worker = worker;
        this.dayOfWeek = dayOfWeek;
        this.serviceStartTime = serviceStartTime;
        this.serviceFinishTime = serviceFinishTime;
    }
    public static WorkerSchedule createWorkerSchedule(Services service,
                                                        Worker worker,
                                                        DayOfWeekType dayOfWeek,
                                                        LocalTime serviceStartTime,
                                                        LocalTime serviceFinishTime) {
        return new WorkerSchedule(service,worker, dayOfWeek, serviceStartTime, serviceFinishTime);
    }

}
