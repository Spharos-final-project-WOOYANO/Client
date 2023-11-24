package spharos.client.service.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MostServiceReviewBookmarkCountResponse {
    private Long serviceId;
    private int bookmarkCount;
    private int reviewCount;
}
