package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.UserService;
import spharos.client.service.vo.response.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
public class UserServiceController {

    private final UserService userService;

    /*
        최근 받은 서비스 상세 내용 조회
    */
    @Operation(summary = "최근 받은 서비스 상세 내용 조회",
            description = "최근 받은 서비스 상세 내용 조회",
            tags = { "User Service" })
    @GetMapping("/user/recent/{serviceId}")
    public BaseResponse<?> getRecentService(
            @RequestHeader("email") String email, @PathVariable("serviceId") Long serviceId) {

        // 최근 받은 서비스 상세 내용 조회
        UserRecentServiceResponse response = userService.getUserRecentService(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        리뷰의 서비스명과 기사명 조회
    */
    @Operation(summary = "리뷰의 서비스명과 기사명 조회",
            description = "리뷰의 서비스명과 기사명 조회",
            tags = { "User Service" })
    @GetMapping("/review/detail")
    public BaseResponse<?> getServiceDetailForReview(@RequestParam("serviceId") Long serviceId,
                                                     @RequestParam("workerId") Long workerId) {

        // 리뷰의 서비스명과 기사명 조회
        ServiceDetailForReviewResponse response = userService.getServiceDetailForReview(serviceId, workerId);
        return new BaseResponse<>(response);
    }

    /*
        찜리스트의 서비스명과 로고 조회
    */
    @Operation(summary = "찜리스트의 서비스명과 로고 조회",
            description = "찜리스트의 서비스명과 로고 조회",
            tags = { "User Service" })
    @GetMapping("/bookmark/detail/{serviceId}")
    public BaseResponse<?> getServiceDetailForBookmark(@PathVariable("serviceId") Long serviceId) {

        // 찜리스트의 서비스명과 로고 조회
        ServiceDetailForBookmarkResponse response = userService.getServiceDetailForBookmark(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        서비스내역 업체와 기사명 조회
    */
    @Operation(summary = "서비스내역 업체와 기사명 조회",
            description = "서비스내역 업체와 기사명 조회",
            tags = { "User Service" })
    @GetMapping("/service/history/list/detail")
    public BaseResponse<?> getServiceDetailForServiceHistoryList(@RequestParam("serviceId") Long serviceId,
                                                       @RequestParam("workerId") Long workerId) {

        // 서비스내역 업체와 기사명 조회
        ServiceDetailForServiceHistoryListResponse response =
                userService.getServiceDetailForServiceHistoryList(serviceId, workerId);
        return new BaseResponse<>(response);
    }

    /*
        서비스 상세 내역의 업체정보 조회
    */
    @Operation(summary = "서비스 상세 내역의 업체정보 조회",
            description = "서비스 상세 내역의 업체정보 조회",
            tags = { "User Service" })
    @GetMapping("/service/history/detail")
    public BaseResponse<?> getServiceDetailForServiceHistory(@RequestParam("serviceId") Long serviceId,
                                                                 @RequestParam("workerId") Long workerId) {

        // 서비스 상세 내역의 업체정보 조회
        ServiceDetailForServiceHistoryResponse response =
                userService.getServiceDetailForServiceHistory(serviceId, workerId);
        return new BaseResponse<>(response);
    }



}
