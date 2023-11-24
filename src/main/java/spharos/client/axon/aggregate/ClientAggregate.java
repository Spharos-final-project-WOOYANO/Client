package spharos.client.axon.aggregate;



import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import spharos.client.axon.command.SendReservationNotificationCommand;
import spharos.client.axon.event.ReservationNotificationEvent;

@Aggregate
@Slf4j
@NoArgsConstructor
public class ClientAggregate {

    @AggregateIdentifier
    private String reservation_num;

    @CommandHandler
    public ClientAggregate(SendReservationNotificationCommand command) {
        log.info("notification command");
        ReservationNotificationEvent notificationEvent = new ReservationNotificationEvent(command.getReservationNum());
        apply(notificationEvent);
    }


    @EventSourcingHandler
    public void createOrder(ReservationNotificationEvent event) {
        log.info("ReservationCreateEvent");
        this.reservation_num = event.getReservationNum();
    }




}
