package spharos.client.service.presentation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import spharos.client.service.vo.response.MostServiceReviewBookmarkCountResponse;

import java.util.List;

@FeignClient(name = "review-service", url = "http://localhost:8000/api/v1/review-bookmark")
public interface BookmarkFeignClient {

    @GetMapping("/best-bookmark/service")
    List<MostServiceReviewBookmarkCountResponse> getBestBookmarkServiceIdList();

}
