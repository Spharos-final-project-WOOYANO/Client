package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWriterResponse {

    private Long workerId;
    private String workerName;
    private Long serviceId;
    private String serviceName;

}
