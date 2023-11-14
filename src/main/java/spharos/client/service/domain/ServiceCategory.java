package spharos.client.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_category")
public class ServiceCategory { //중간테이블 - service와 category의 FK를 모두가지고 있음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    public ServiceCategory(Services service,
                           Category category) {
        this.service = service;
        this.category = category;
    }
    public static ServiceCategory createServiceCategory(Services service,
                                                         Category category) {
        return new ServiceCategory(service, category);
    }
}
