package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ExcellentServiceResponse {
    private String name;
    private List<String> imgUrl;
    private String description;
    private String address;
    private Long servieId;
    private int bookmarkCount;
    private int reviewCount;
}
