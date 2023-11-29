package spharos.client.reservation.presentation;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spharos.client.reservation.domain.Reservation;
import spharos.client.reservation.dto.ReservationListResponse;

@FeignClient(name = "reservation-service", url = "http://localhost:8000/api/v1/reservation")
public interface ReservationFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/wait-list")
    List<Reservation>  getReservationList(@RequestParam("serviceId") Long serviceId);
}
