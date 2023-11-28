package spharos.client.service.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.RetrieveServiceDetailService;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;
import spharos.client.service.dto.SearchServiceDataListDto;
import spharos.client.service.dto.ServiceDetailDto;
import spharos.client.service.vo.response.SearchServiceDataListResponse;
import spharos.client.service.vo.response.ServiceDetailResponse;
import spharos.client.service.vo.response.ExcellentServiceResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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
                .serviceId(serviceId)
                .description(serviceDetailDto.getDescription())
                .name(serviceDetailDto.getClientName())
                .serviceAreaList(serviceDetailDto.getServiceAreaList())
                .registrationNumber(serviceDetailDto.getRegistrationNumber())
                .clientAddress(serviceDetailDto.getClientAddress())
                .serviceImgUrlList(serviceDetailDto.getServiceImgUrlList())
                .serviceHeaderImgUrl(serviceDetailDto.getServiceHeaderImgUrl())
                .build();

        return new BaseResponse<>(serviceDetailResponse);
    }
    @Operation(summary = "서비스 날짜,지역,타입으로 검색",
            description = "서비스 날짜,지역,타입으로 검색",
            tags = { "Service Search" })
    @GetMapping("/search") // required=false 옵션으로 해당 requestParam이 null일 경우에도 정상적으로 동작하도록 설정
    public BaseResponse<List<SearchServiceDataListResponse>> searchList(@RequestParam("type") String type , @RequestParam(value="date",required=false) LocalDate date , @RequestParam("region") Integer region) throws ParseException {

        List<Long> possibleServiceIdList;

        if (type.equals("1") || type.equals("4") ){
            possibleServiceIdList = searchService.findWorkerServiceList(type,date,region);
        }
        else {
            possibleServiceIdList = searchService.findServiceList(type,region);
        }
        List<SearchServiceDataListResponse> responseList = searchService.findServiceListData(possibleServiceIdList,type).stream()
                .map(dto -> SearchServiceDataListResponse.builder()
                        .name(dto.getName())
                        .imgUrl(dto.getImgUrl())
                        .type(dto.getType())
                        .serviceId(dto.getServiceId())
                        .description(dto.getDescription())
                        .address(dto.getAddress())
                        .build())
                .toList();

        return new BaseResponse<>(responseList);

    }

    @Operation(summary = "서비스 타입으로 검색",
            description = "서비스 타입으로 업체 리스트 검색",
            tags = { "Service Type ListSearch" })
    @GetMapping("/service")
    public BaseResponse<?> serviceTypeList(@RequestParam("type") String type) {

        List<SearchServiceDataListDto> searchServiceDtoList = searchService.findServiceTypeSearch(type);

        List<SearchServiceDataListResponse> searchServiceDataListResponseList = searchServiceDtoList.stream()
                .map(searchServiceData -> SearchServiceDataListResponse.builder()
                        .name(searchServiceData.getName())
                        .imgUrl(searchServiceData.getImgUrl())
                        .type(searchServiceData.getType())
                        .serviceId(searchServiceData.getServiceId())
                        .description(searchServiceData.getDescription())
                        .address(searchServiceData.getAddress())
                        .build())
                .toList();

        return new BaseResponse<>(searchServiceDataListResponseList);
    }

    @Operation(summary = "우수 업체 리스트 검색",
            description = "찜 수를 기준으로 우수 업체 리스트 검색",
            tags = { "Exellent ListSearch" })
    @GetMapping("/excellent-service")
    public BaseResponse<?> getExcellentServiceList(){

    List<ExcellentServiceResponse> responseList = searchService.findExcellentServiceList().stream()
            .map(dto -> ExcellentServiceResponse.builder()
                    .servieId(dto.getServieId())
                    .name(dto.getName())
                    .imgUrl(dto.getImgUrl())
                    .description(dto.getDescription())
                    .address(dto.getAddress())
                    .bookmarkCount(dto.getBookmarkCount())
                    .reviewCount(dto.getReviewCount())
                    .build())
            .toList();

        return new BaseResponse<>(responseList);
    }
}
