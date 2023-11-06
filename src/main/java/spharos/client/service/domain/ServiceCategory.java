package spharos.client.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "service_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceCategory { //중간테이블 - service와 category의 FK를 모두가지고 있음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public ServiceCategory(Service service, Category category) {
        this.service = service;
        this.category = category;
    }

    public static ServiceCategory createServiceCategory(Service service, Category category){
        return new ServiceCategory(service,category);
    }
}
