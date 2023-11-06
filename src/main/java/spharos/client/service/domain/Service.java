package spharos.client.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "service")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Service {

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

    @Column(nullable = false,length = 50,name = "address")
    private String address;

    @Column(nullable = false,length = 100,name = "area")
    private String area;

    public Service(String logoUrl, String description, String headerImgUrl, String name, String address, String area) {
        this.logoUrl = logoUrl;
        this.description = description;
        this.headerImgUrl = headerImgUrl;
        this.name = name;
        this.address = address;
        this.area = area;
    }
    public static Service createService(String logoUrl,String description,String headerImgUrl
                                        ,String name,String address,String area){
    //접근 지정자를 static으로 줘서 외부에서도 접근가능하게 함
        return new Service(logoUrl,description,headerImgUrl,name,address,area);
        
    }

}
