package spharos.client.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchServiceDataListDto {

    private String name;
    private String type;
    private Long serviceId;
    private List<String> imgUrl;
    private String description;
    private String address;
}
