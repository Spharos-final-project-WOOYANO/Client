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
public class SearchServiceDataListResponse {

    private String name;
    private List<String> imgUrl;
    private String description;
    private String address;
}
