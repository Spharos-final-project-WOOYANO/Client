package spharos.client.clients.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spharos.client.service.domain.Service;

@Entity
@Table(name = "service_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class ServiceImage {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,length = 255,name = "img_url")
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    public ServiceImage(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static ServiceImage createServiceImage(String imgUrl){
        return new ServiceImage(imgUrl);
    }
}
