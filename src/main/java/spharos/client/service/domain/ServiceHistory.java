package spharos.client.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "service_history")
public class ServiceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, name = "reservation_goods_id")
    private Long reservationGoodsId;
    @Column(nullable = false, length = 50, name = "worker_id")
    private Long workerId;
    @Column(nullable = false, length = 50, name = "service_start_time")
    private LocalDateTime serviceStartTime;
    @Column(nullable = false, length = 50, name = "service_end_time")
    private LocalDateTime serviceEndTime;
    @Column(nullable = false, length = 50, name = "service_status")
    private Integer serviceStatus;

    public ServiceHistory(Long reservationGoodsId,
                          Long workerId,
                          LocalDateTime serviceStartTime,
                          LocalDateTime serviceEndTime,
                          Integer serviceStatus) {
        this.reservationGoodsId = reservationGoodsId;
        this.workerId = workerId;
        this.serviceStartTime = serviceStartTime;
        this.serviceEndTime = serviceEndTime;
        this.serviceStatus = serviceStatus;
    }

}
