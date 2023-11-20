package spharos.client.service.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ServiceAreaRegisterRequest {

    private Long serviceId;
    private List<Integer> areaCodeList;

}
