package spharos.client.service.application;

import spharos.client.service.dto.ReviewWriterDto;
import java.util.List;

public interface ReviewService {

    List<ReviewWriterDto> retrieveReviewWriter(List<String> reservationNumList);
}
