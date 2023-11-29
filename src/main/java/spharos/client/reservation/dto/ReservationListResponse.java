package spharos.client.reservation.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.reservation.domain.ReservationGoods;
import spharos.client.reservation.domain.ReservationState;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReservationListResponse {


    private ReservationGoods reservationGoods;

    private String userEmail;
    private Long serviceId;
    private Long workerId;
    private LocalDate reservationDate;
    private LocalTime serviceStart;
    private LocalTime serviceEnd;
    private ReservationState reservationState;
    private Integer paymentAmount;
    private String cancelDesc;
    private String request;
    private String reservationNum;
    private String address;

}
