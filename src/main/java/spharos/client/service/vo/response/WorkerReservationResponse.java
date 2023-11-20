package spharos.client.service.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class WorkerReservationResponse {

    private List<Long> serviceId; //작업자가 속한 업체의 id
    public WorkerReservationResponse(List<Long> serviceId) {
        this.serviceId = serviceId;
    }
}
