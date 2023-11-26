package spharos.client.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationPossibleWorkerDto {

    private Long workerId;
    private String workerName;
    private List<LocalTime> workerPossibleTime;
}
