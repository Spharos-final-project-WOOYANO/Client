package spharos.client.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
//클라이언트에 homekeeper 서비스 업체 리스트를 담아서 전송하는 VO객체
//AllArgs~~의 사용을 지양하는것이 좋다고해서 추후 리팩토링때 수정할 예정
public class HomeKeeperServiceResponse {
    private String logoUrl;
    private String description;
    private String headerImgUrl;
    private String name;
    private String address;
    private String area;
}
