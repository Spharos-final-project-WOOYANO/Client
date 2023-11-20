package spharos.client.service.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ClientRegisterServiceRequest {

    private String logoUrl;
    private String description;
    private String headerImgUrl;
    private String name;
    private String address;
    private List<String> serviceImageUrlList;

}
