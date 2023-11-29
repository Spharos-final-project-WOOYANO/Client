package spharos.client.reservation.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import spharos.client.reservation.dto.ReservationDecisionRequest;

public interface ReservationService {
    public Long getServiceId(String clientEmail);

    public void acceptReservation(ReservationDecisionRequest reservationId) throws JsonProcessingException;
}
