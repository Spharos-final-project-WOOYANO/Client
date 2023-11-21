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
public class ServiceDetailResponse {

    private String description;
    private String name;
    private List<Integer> serviceAreaList;
    private String registrationNumber;
    private String cliendAddress;
}
