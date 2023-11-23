package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.dto.ReviewWriterDto;
import spharos.client.worker.domain.WorkerReservationHistory;
import spharos.client.worker.infrastructure.WorkerReservationHistoryRepository;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final WorkerReservationHistoryRepository workerReservationRepository;
    @Override
    public List<ReviewWriterDto> retrieveReviewWriter(List<String> reservationNumList){

        List<WorkerReservationHistory> historyList = reservationNumList.stream()
                .map(workerReservationRepository::findByReservationNum)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        //reservaton에 예약번호를 전달하고 해당하는 작업자Id,
        return historyList.stream()
                .map(history -> ReviewWriterDto.builder()
                        .workerName(history.getWorker().getName())
                        .serviceName(history.getWorker().getService().getName())
                        .build())
                .toList();
    }
}
