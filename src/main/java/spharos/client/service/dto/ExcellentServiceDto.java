package spharos.client.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcellentServiceDto {

    private String name;
    private List<String> imgUrl;
    private String description;
    private String address;
    private Long servieId;
    private int bookmarkCount;
    private int reviewCount;
}
