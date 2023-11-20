package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.ClientMypageService;
import spharos.client.service.vo.request.ClientModifyServiceRequest;
import spharos.client.service.vo.request.ClientRegisterServiceRequest;
import spharos.client.service.vo.response.ClientRegisterServiceResponse;
import spharos.client.service.vo.response.ClientServiceResponse;

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

}
