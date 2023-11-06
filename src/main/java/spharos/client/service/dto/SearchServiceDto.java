package spharos.client.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchServiceDto {

        private String logoUrl;
        private String description;
        private String headerImgUrl;
        private String name;
        private String address;
        private String area;

}
