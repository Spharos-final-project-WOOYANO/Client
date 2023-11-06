package spharos.client.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
    // 44~48 line은 개인 공부중 필기용으로 작성해둔거라 무시하시면 됩니다~(1106 - 신영)
    //※정적 팩토리 메서드란
    //-엔티티 클래스 내부에서 객체를 생성하여 리턴한다.
    //-어노테이션은 주로 객체의 상태나 동작과 관련된 것들을 표시하기 위해 사용되지만,
    // 정적 팩토리 메서드는 객체를 생성하는 메서드이기 때문에 어노테이션이 적용되지 않는다.
    public static Service createService(String logoUrl,String description,String headerImgUrl
                                        ,String name,String address,String area){
    //접근 지정자를 static으로 줘서 외부에서도 접근가능하게 함
        return new Service(logoUrl,description,headerImgUrl,name,address,area);
        
    }

}
