package spharos.client.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewWriterDto {

    private Long workerId;
    private String workerName;
    private Long serviceId;
    private String serviceName;

}
