package spharos.client.worker.application;

import spharos.client.worker.dto.WorkerDetailDto;
import java.util.List;

public interface WorkerService {

    List<WorkerDetailDto> retrieveWorkerList(Long ServiceId);
}
