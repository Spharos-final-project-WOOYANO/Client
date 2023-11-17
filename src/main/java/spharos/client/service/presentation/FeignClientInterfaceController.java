package spharos.client.service.presentation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spharos.client.service.vo.request.ServiceIdListRequest;
import spharos.client.service.vo.response.WorkerReservationResponse;

@FeignClient(name = "reservation-service",url = "http://reservation-service/api/v1/reservation/")
public interface FeignClientInterfaceController {

    @GetMapping("/check/all-worker/reservation")
    WorkerReservationResponse checkAllWorkerReservation(@RequestBody ServiceIdListRequest request);

}
