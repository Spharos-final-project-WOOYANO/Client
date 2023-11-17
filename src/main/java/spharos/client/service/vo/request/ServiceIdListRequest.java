package spharos.client.service.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ServiceIdListRequest {
    private List<Long> serviceId;

    public ServiceIdListRequest(List<Long> serviceId) {
        this.serviceId = serviceId;
    }
}
