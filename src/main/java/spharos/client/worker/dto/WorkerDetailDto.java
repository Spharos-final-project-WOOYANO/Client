package spharos.client.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDetailDto {

        private String name;
        private String imgUrl;
        private String description;
}
