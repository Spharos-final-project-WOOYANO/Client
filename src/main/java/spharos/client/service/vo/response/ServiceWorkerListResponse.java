package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.service.dto.ServiceWorkerScheduleDto;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceWorkerListResponse {

    private Long workerId;
    private String imgUrl;
    private String name;
    private String phone;
    private String description;
    private List<ServiceWorkerScheduleDto> scheduleList;

}
