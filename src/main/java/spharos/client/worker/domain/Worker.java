package spharos.client.worker.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.service.domain.services.Services;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, name = "name")
    private String name;

    @Column(nullable = false, length = 20, name = "phone")
    private String phone;

    @Column(nullable = false, length = 255, name = "description")
    private String description;

    @Column(nullable = false, length = 255, name = "img_url")
    private String imgUrl;

    @Column(nullable = false, name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Services service;

    public Worker(
                  String name,
                  String phone,
                  String description,
                  String imgUrl,
                  Boolean status,
                  Services service) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.imgUrl = imgUrl;
        this.status = status;
        this.service = service;
    }

    public static Worker createWorker(
                                      String name,
                                      String phone,
                                      String description,
                                      String imgUrl,
                                      Boolean status,
                                      Services service) {
        return new Worker(name, phone, description, imgUrl, status, service);
    }
}
