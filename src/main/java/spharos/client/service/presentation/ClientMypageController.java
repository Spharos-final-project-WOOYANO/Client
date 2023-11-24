package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.ClientMypageService;
import spharos.client.service.vo.request.*;
import spharos.client.service.vo.response.ClientRegisterServiceResponse;
import spharos.client.service.vo.response.ClientServiceResponse;
import spharos.client.service.vo.response.ServiceAreaResponse;
import spharos.client.service.vo.response.ServiceWorkerListResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client/mypage")
public class ClientMypageController {

    private final ClientMypageService clientMypageService;

    /*
        매장정보 조회
    */
    @Operation(summary = "매장정보 조회",
            description = "매장정보 조회",
            tags = { "Client Mypage" })
    @GetMapping("/service/{serviceId}")
    public BaseResponse<?> getClientService(@PathVariable("serviceId") Long serviceId) {

        // 매장정보 조회
        ClientServiceResponse response = clientMypageService.getClientService(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        매장정보 등록
    */
    @Operation(summary = "매장정보 등록",
            description = "매장정보 등록",
            tags = { "Client Mypage" })
    @PostMapping("/service")
    public BaseResponse<?> registerClientService(@RequestHeader("email") String email,
                                                 @RequestBody ClientRegisterServiceRequest request) {

        // 매장정보 등록
        Long serviceId = clientMypageService.registerService(email, request);

        ClientRegisterServiceResponse response = ClientRegisterServiceResponse.builder()
                .serviceId(serviceId)
                .build();

        return new BaseResponse<>(response);
    }

    /*
        매장정보 수정
    */
    @Operation(summary = "매장정보 수정",
            description = "매장정보 수정",
            tags = { "Client Mypage" })
    @PutMapping("/service")
    public BaseResponse<?> modifyClientService(@RequestBody ClientModifyServiceRequest request) {

        // 매장정보 수정
        clientMypageService.modifyService(request);
        return new BaseResponse<>();
    }

    /*
        서비스 가능 지역 등록
    */
    @Operation(summary = "서비스 가능 지역 등록",
            description = "서비스 가능 지역 등록",
            tags = { "Client Mypage" })
    @PostMapping("/service/area")
    public BaseResponse<?> registerClientServiceArea(@RequestBody ServiceAreaRegisterRequest request) {

        // 서비스 가능 지역 등록
        clientMypageService.registerServiceArea(request);
        return new BaseResponse<>();
    }

    /*
        서비스 가능 지역 조회
    */
    @Operation(summary = "서비스 가능 지역 조회",
            description = "서비스 가능 지역 조회",
            tags = { "Client Mypage" })
    @GetMapping("/service/area/{serviceId}")
    public BaseResponse<?> getClientServiceArea(@PathVariable("serviceId") Long serviceId) {

        // 서비스 가능 지역 조회
        ServiceAreaResponse response = clientMypageService.getServiceArea(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        서비스 가능 지역 수정
    */
    @Operation(summary = "서비스 가능 지역 수정",
            description = "서비스 가능 지역 수정",
            tags = { "Client Mypage" })
    @PutMapping("/service/area")
    public BaseResponse<?> modifyClientServiceArea(@RequestBody ServiceAreaModifyRequest request) {

        // 서비스 가능 지역 수정
        clientMypageService.modifyService(request);
        return new BaseResponse<>();
    }

    /*
        운영정보 조회
    */


    /*
        운영정보 등록
    */


    /*
        운영정보 수정
    */


    /*
        작업자 리스트 조회
    */
    @Operation(summary = "작업자 리스트 조회",
            description = "작업자 리스트 조회",
            tags = { "Client Mypage" })
    @GetMapping("/service/worker/list/{serviceId}")
    public BaseResponse<?> getClientServiceWorkerList(@PathVariable("serviceId") Long serviceId) {

        // 작업자 리스트 조회
        List<ServiceWorkerListResponse> response = clientMypageService.getServiceWorkerList(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        작업자 추가
    */
    @Operation(summary = "작업자 추가",
            description = "작업자 추가",
            tags = { "Client Mypage" })
    @PostMapping("/service/worker")
    public BaseResponse<?> registerClientServiceWorker(@RequestBody ServiceWorkerRegisterRequest request) {

        // 작업자 추가
        clientMypageService.registerServiceWorker(request);
        return new BaseResponse<>();
    }


    /*
        작업자 수정
    */


    /*
        작업자 삭제
    */
    @Operation(summary = "작업자 삭제",
            description = "작업자 삭제",
            tags = { "Client Mypage" })
    @DeleteMapping("/service/worker/{workerId}")
    public BaseResponse<?> deleteClientServiceWorker(@PathVariable("workerId") Long workerId) {

        // 작업자 추가
        clientMypageService.deleteServiceWorker(workerId);
        return new BaseResponse<>();
    }

}
