package spharos.client.reservation.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.reservation.dto.ReservationDecisionRequest;
import spharos.client.reservation.infrastructure.ReservationClientRepository;
import spharos.client.reservation.infrastructure.ReservationClientServiceListRepository;
import spharos.client.reservation.infrastructure.ReservationServiceRepository;
import spharos.client.reservation.producer.ReservationDecisionEventsProducer;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationServiceRepository serviceRepository;
    private final ReservationClientRepository clientRepository;
    private final ReservationClientServiceListRepository clientServiceListRepository;
    private final ReservationDecisionEventsProducer producer;

    public Long getServiceId(String clientEmail) {
        Long byClientId = clientRepository.findByClientId(clientEmail);
        Long byServicesId = clientServiceListRepository.findByServicesId(byClientId);
        return byServicesId;


    }

    @Override
    public void acceptReservation(ReservationDecisionRequest request) throws JsonProcessingException {
         producer.sendLibraryEvent(request);
    }
}
