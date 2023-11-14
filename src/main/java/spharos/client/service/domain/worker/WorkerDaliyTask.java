package spharos.client.service.domain.worker;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "worker_daily_task")
public class WorkerDaliyTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, name = "applicable_date")
    private LocalDate applicableDate;

    @Column(nullable = false, length = 20, name = "attandance_time")
    private LocalDate attandanceTime;

    @Column(nullable = false, length = 20, name = "leave_time")
    private LocalDate leaveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Worker worker;
    public WorkerDaliyTask(LocalDate applicableDate,
                           LocalDate attandanceTime,
                           LocalDate leaveTime,
                           Worker worker) {
        this.applicableDate = applicableDate;
        this.attandanceTime = attandanceTime;
        this.leaveTime = leaveTime;
        this.worker = worker;
    }

    public static WorkerDaliyTask createWorkerDaliyTask(LocalDate applicableDate,
                                                        LocalDate attandanceTime,
                                                        LocalDate leaveTime,
                                                        Worker worker) {
        return new WorkerDaliyTask(applicableDate, attandanceTime, leaveTime, worker);
    }

}
