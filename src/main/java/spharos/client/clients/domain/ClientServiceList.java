package spharos.client.clients.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.service.domain.services.Services;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client_service_list")
public class ClientServiceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "service_id")
    private Services services;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    private ClientServiceList(Services services, Client client) {
        this.services = services;
        this.client = client;
    }

    public static ClientServiceList createClientServiceList(Services services, Client client) {
        return new ClientServiceList(services, client);
    }

}
