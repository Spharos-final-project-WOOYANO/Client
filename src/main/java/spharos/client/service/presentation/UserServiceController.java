package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.UserService;
import spharos.client.service.vo.response.UserRecentServiceResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
public class UserServiceController {

    private final UserService userService;

    @Operation(summary = "최근 받은 서비스 상세 내용 조회",
            description = "최근 받은 서비스 상세 내용 조회",
            tags = { "User Service" })
    @GetMapping("/user/recent/{serviceId}")
    public BaseResponse<?> changePassword(
            @RequestHeader("email") String email, @PathVariable("serviceId") Long serviceId) {

        // 최근 받은 서비스 상세 내용 조회
        UserRecentServiceResponse response = userService.getUserRecentService(serviceId);
        return new BaseResponse<>(response);
    }

}
