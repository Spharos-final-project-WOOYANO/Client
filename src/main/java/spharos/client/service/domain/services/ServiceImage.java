package spharos.client.service.domain.services;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table(name = "service_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class ServiceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 255,name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Services service;

    public ServiceImage(String imgUrl,
                        Services service) {
        this.imgUrl = imgUrl;
        this.service = service;
    }
    public static ServiceImage createServiceImage(String imgUrl,
                                                  Services service) {
        return new ServiceImage(imgUrl, service);
    }
}
