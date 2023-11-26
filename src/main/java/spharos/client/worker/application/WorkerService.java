package spharos.client.worker.application;

import spharos.client.worker.dto.ReservationPossibleWorkerDto;
import spharos.client.worker.dto.WorkerDetailDto;
import java.time.LocalDate;
import java.util.List;

public interface WorkerService {

    List<WorkerDetailDto> retrieveWorkerList(Long ServiceId);

    ReservationPossibleWorkerDto findPossibleWorker(Long serviceId,Long workerId,LocalDate date);
}
