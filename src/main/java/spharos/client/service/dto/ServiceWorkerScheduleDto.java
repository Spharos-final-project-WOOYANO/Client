package spharos.client.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceWorkerScheduleDto {

    private Integer dayOfWeek;
    private LocalTime serviceStartTime;
    private LocalTime serviceFinishTime;

}
