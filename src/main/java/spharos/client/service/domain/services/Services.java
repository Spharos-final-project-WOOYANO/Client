package spharos.client.service.domain.services;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "service")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //고유값
    private Long id;

    @Column(nullable = false, length = 255,name = "logo_url")
    private String logoUrl;

    @Column(nullable = false, length = 500,name = "description")
    private String description;

    @Column(nullable = false,length = 255,name = "header_img_url")
    private String headerImgUrl;

    @Column(nullable = false,length = 30,name = "name")
    private String name;
    // 서비스 사무실 주소(!= 서비스 가능주소)
    @Column(nullable = false,length = 50,name = "address")
    private String address;


    public Services(String logoUrl,
                    String description,
                    String headerImgUrl,
                    String name,
                    String address) {
        this.logoUrl = logoUrl;
        this.description = description;
        this.headerImgUrl = headerImgUrl;
        this.name = name;
        this.address = address;
    }
    public static Services createService(String logoUrl,
                                            String description,
                                            String headerImgUrl,
                                            String name,
                                            String address){
        //접근 지정자를 static으로 줘서 외부에서도 접근가능하게 함
        return new Services(logoUrl,description,headerImgUrl,name,address);
        
    }

    // 서비스 수정
    public void modifyService(String logoUrl, String description, String headerImgUrl,
                                String name, String address) {
        this.logoUrl = logoUrl;
        this.description = description;
        this.headerImgUrl = headerImgUrl;
        this.name = name;
        this.address = address;
    }

}
