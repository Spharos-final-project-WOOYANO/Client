package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.service.application.RetrieveServiceDetailService;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;
import spharos.client.service.dto.ServiceDetailDto;
import spharos.client.service.vo.response.SearchServiceDateListResponse;
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

//    @GetMapping("/service")
//    public void houseKeeprList(@RequestParam("type") String type){
//        retrieveServiceDetailService.retrieveServiceDetail(typ;
//
//    }
    @GetMapping("/service-detail")
    public BaseResponse<ServiceDetailResponse> serviceDetail (@RequestParam("serviceId") Long serviceId){

        ServiceDetailDto serviceDetailDto = retrieveServiceDetailService.retrieveServiceDetail(serviceId);

        ServiceDetailResponse serviceDetailResponse = ServiceDetailResponse.builder()
                .description(serviceDetailDto.getDescription())
                .name(serviceDetailDto.getClientName())
                .serviceAreaList(serviceDetailDto.getServiceAreaList())
                .registrationNumber(serviceDetailDto.getRegistrationNumber())
                .cliendAddress(serviceDetailDto.getCliendAddress())
                .build();

        return new BaseResponse<>(serviceDetailResponse);
    }

    @GetMapping("/search") // required=false 옵션으로 해당 requestParam이 null일 경우에도 정상적으로 동작하도록 설정
    public BaseResponse<List<SearchServiceDateListResponse>> searchList(@RequestParam("type") String type , @RequestParam(value="date",required=false) LocalDate date , @RequestParam("region") Integer region) throws ParseException {

        List<Long> possibleServiceIdList = searchService.findServiceList(type,date,region);

        List<SearchServiceDateListResponse> searchServiceDtoList = searchService.findServiceListData(possibleServiceIdList);

        return new BaseResponse<>(searchServiceDtoList);

    }


}
