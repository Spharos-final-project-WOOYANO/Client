package spharos.client.worker.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReservationPossibleWorkerResponse {

    private Long workerId;
    private String workerName;
    private List<LocalTime> workerPossibleTime;
}
