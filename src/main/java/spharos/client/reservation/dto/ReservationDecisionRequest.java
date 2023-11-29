package spharos.client.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spharos.client.reservation.domain.ReservationState;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDecisionRequest {
    private String reservationNum;
    private ReservationState reservationState;
}
