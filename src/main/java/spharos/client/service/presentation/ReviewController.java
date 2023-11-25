package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.service.application.ReviewService;
import spharos.client.service.vo.response.ReviewWriterResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping("/review/service")
    public List<ReviewWriterResponse> getReviewWriter(@RequestBody List<String> reservationNumList){

                return reviewService.retrieveReviewWriter(reservationNumList).stream()
                .map(reviewWriterDto -> ReviewWriterResponse.builder()
                        .serviceId(reviewWriterDto.getServiceId())
                        .serviceName(reviewWriterDto.getServiceName())
                        .workerId(reviewWriterDto.getWorkerId())
                        .workerName(reviewWriterDto.getWorkerName())
                        .build())
                .toList();

    }
}
