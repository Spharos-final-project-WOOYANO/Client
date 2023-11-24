package spharos.client.axon.event.handlle;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import spharos.client.axon.event.ReservationNotificationEvent;

@Slf4j
@Component
@AllArgsConstructor
@ProcessingGroup("notification")
public class NotificationEventHandler {

    @EventHandler
    public void create( ReservationNotificationEvent event) {
        log.info("notification eventhandler");

    }

}

