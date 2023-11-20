package spharos.client.service.domain.services;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_area")
@Getter
public class ServiceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "service_id")
    private Services services;

    //서비스 가능지역 코드 column
    @Column(name = "area_code")
    private Integer areaCode;

    public ServiceArea(Services services,
                       Integer areaCode) {
        this.services = services;
        this.areaCode = areaCode;
    }
    public static ServiceArea createServiceArea(Services services,
                                                Integer areaCode) {
        return new ServiceArea(services, areaCode);
    }

}
