package spharos.client.service.vo.request;

import lombok.Getter;
import spharos.client.service.dto.ServiceWorkerScheduleRegisterDto;

import java.util.List;

@Getter
public class ServiceWorkerRegisterRequest {

    private Long serviceId;
    private String name;
    private String phone;
    private String description;
    private String imgUrl;
    private List<ServiceWorkerScheduleRegisterDto> workerScheduleDtoList;

}
