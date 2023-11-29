package spharos.client.reservation.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.reservation.application.ReservationService;
import spharos.client.reservation.domain.Reservation;
import spharos.client.reservation.dto.ReservationDecisionRequest;
import spharos.client.reservation.dto.ReservationListResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client/reservation")
public class ReservationController {
    private final ReservationFeignClient reservationFeignClient;

    private final ReservationService reservationService;

    //예약 대기 목록 조회
    @GetMapping("/wait-list")
    public  List<Reservation>  getWaitList(@RequestParam String clientEmail) {
        Long serviceId = reservationService.getServiceId(clientEmail);
        log.info("clientId: {}", serviceId);
        return reservationFeignClient.getReservationList(serviceId);
    }

    //예약 대기 수락 (예약 상태 변경)
    @PostMapping("/accept")
    public BaseResponse<?> acceptReservation(@RequestBody ReservationDecisionRequest request) throws JsonProcessingException {
        reservationService.acceptReservation(request);
        return new BaseResponse<>();
    }


}
