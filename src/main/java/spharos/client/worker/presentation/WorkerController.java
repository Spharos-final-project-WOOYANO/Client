package spharos.client.worker.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.worker.application.WorkerService;
import spharos.client.worker.vo.response.WorkerReservationResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/worker")
@Slf4j
public class WorkerController {

    private final WorkerService workerService;
    @Operation(summary = "작업자 리스트 조회",
            description = "작업자 리스트 조회",
            tags = { "Worker List Retrieve" })
    @GetMapping("/list")
    public BaseResponse<List<WorkerReservationResponse>> retrieveWorkerList(@RequestParam("serviceId") Long serviceId){

        List<WorkerReservationResponse> responseBodyList = workerService.retrieveWorkerList(serviceId).stream()
                .map(dto -> WorkerReservationResponse.builder()
                        .workerId(dto.getWorkerId())
                        .serviceId(serviceId)
                        .name(dto.getName())
                        .imgUrl(dto.getImgUrl())
                        .description(dto.getDescription())
                        .build())
                .toList();
        return new BaseResponse<>(responseBodyList);
    }
}
