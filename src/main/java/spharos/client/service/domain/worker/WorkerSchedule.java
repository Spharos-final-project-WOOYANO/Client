package spharos.client.service.domain.worker;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "worker_schedule")
public class WorkerSchedule {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private WorkerDaliyTask workerDaliyTask;

    @Column(nullable = false, length = 20, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, length = 20, name = "reservation_possible_status")
    private Boolean reservationPossibleStatus;

    public WorkerSchedule(LocalDateTime startTime,
                          Boolean reservationPossibleStatus) {
        this.startTime = startTime;
        this.reservationPossibleStatus = reservationPossibleStatus;
    }
    public static WorkerSchedule createWorkerSchedule(LocalDateTime startTime,
                                                      Boolean reservationPossibleStatus) {
        return new WorkerSchedule(startTime, reservationPossibleStatus);
    }
}
