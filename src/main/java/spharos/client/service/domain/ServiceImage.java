package spharos.client.service.domain;

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
    @GeneratedValue
    private Long id;
    @Column(nullable = false,length = 255,name = "img_url")
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;

}
