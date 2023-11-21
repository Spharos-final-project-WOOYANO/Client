package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailForServiceHistoryResponse {

    private String logoUrl;
    private String serviceName;
    private String address;
    private String workerName;

}
