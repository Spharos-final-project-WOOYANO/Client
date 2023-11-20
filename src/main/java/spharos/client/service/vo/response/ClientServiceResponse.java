package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientServiceResponse {

    private Long serviceId;
    private String logoUrl;
    private String description;
    private String headerImgUrl;
    private String name;
    private String address;
    private List<String> serviceImageUrlList;

}
