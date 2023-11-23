package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.RetrieveServiceDetailService;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;
import spharos.client.service.dto.ServiceDetailDto;
import spharos.client.service.vo.response.SearchServiceDataListResponse;
import spharos.client.service.vo.response.ServiceDetailResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
public class ServiceController {

    private final SearchService searchService;
    private final RetrieveServiceDetailService retrieveServiceDetailService;
    @Operation(summary = "서비스 상세 정보 조회",
            description = "서비스 상세 정보 조회",
            tags = { "Service Detail" })
    @GetMapping("/service-detail")
    public BaseResponse<ServiceDetailResponse> serviceDetail (@RequestParam("serviceId") Long serviceId){

        ServiceDetailDto serviceDetailDto = retrieveServiceDetailService.retrieveServiceDetail(serviceId);

        ServiceDetailResponse serviceDetailResponse = ServiceDetailResponse.builder()
                .description(serviceDetailDto.getDescription())
                .name(serviceDetailDto.getClientName())
                .serviceAreaList(serviceDetailDto.getServiceAreaList())
                .registrationNumber(serviceDetailDto.getRegistrationNumber())
                .clientAddress(serviceDetailDto.getClientAddress())
                .build();

        return new BaseResponse<>(serviceDetailResponse);
    }
    @Operation(summary = "서비스 날짜,지역,타입으로 검색",
            description = "서비스 날짜,지역,타입으로 검색",
            tags = { "Service Search" })
    @GetMapping("/search") // required=false 옵션으로 해당 requestParam이 null일 경우에도 정상적으로 동작하도록 설정
    public BaseResponse<List<SearchServiceDataListResponse>> searchList(@RequestParam("type") String type , @RequestParam(value="date",required=false) LocalDate date , @RequestParam("region") Integer region) throws ParseException {

        List<Long> possibleServiceIdList = searchService.findServiceList(type,date,region);

        List<SearchServiceDataListResponse> searchServiceDtoList = searchService.findServiceListData(possibleServiceIdList);

        return new BaseResponse<>(searchServiceDtoList);

    }


}
