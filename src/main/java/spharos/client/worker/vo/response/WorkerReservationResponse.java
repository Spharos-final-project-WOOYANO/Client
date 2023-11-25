package spharos.client.worker.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerReservationResponse {

    private Long workerId;
    private Long serviceId;
    private String name;
    private String description;
    private String imgUrl;
}
