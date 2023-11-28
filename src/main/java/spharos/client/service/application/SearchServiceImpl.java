package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.domain.services.ServiceImage;
import spharos.client.service.domain.services.Services;
import spharos.client.service.dto.SearchServiceDataListDto;
import spharos.client.service.infrastructure.*;
import spharos.client.service.presentation.BookmarkFeignClient;
import spharos.client.service.vo.response.SearchServiceDataListResponse;
import spharos.client.worker.domain.Worker;
import spharos.client.worker.domain.WorkerReservationHistory;
import spharos.client.worker.domain.WorkerSchedule;
import spharos.client.worker.domain.converter.DayOfWeekConverter;
import spharos.client.worker.domain.enumType.DayOfWeekType;
import spharos.client.service.dto.ExcellentServiceDto;
import spharos.client.worker.infrastructure.WorkerReservationHistoryRepository;
import spharos.client.worker.infrastructure.WorkerRepository;
import spharos.client.worker.infrastructure.WorkerScheduleRepository;
import spharos.client.service.vo.response.MostServiceReviewBookmarkCountResponse;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final WorkerReservationHistoryRepository workerReservationHistoryRepository;
    private final WorkerRepository workerRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    private final ServiceImageRepository serviceImageRepository;
    private final ServicesRepository servicesRepository;
    private final BookmarkFeignClient bookmarkFeignClient;

    @Override
    public List<Long> findServiceList(String type, LocalDate date, Integer region) {

        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);
        log.info("type : {}", type);
        log.info("serviceType : {}", serviceType);List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAllByCategoryBaseCategory(serviceType);

        // 1. 해당 지역에 해당하는 타입의 서비스를 제공하는 업체들을 조회한다.
        List<Long> serviceIdList = serviceAreaRepository.findByAreaCode(region).stream()
                .filter(serviceArea -> serviceCategoryRepository.findAllByCategoryBaseCategory(serviceType).stream()
                        .anyMatch(serviceCategory -> serviceCategory.getService().getId().equals(serviceArea.getServices().getId())))
                .map(serviceArea -> serviceArea.getServices().getId())
                .toList();

        // 1-2. date가 null일 경우 현재 날짜로 설정
        if (date == null) {
            date = LocalDate.now();
        }
        // 1-3.서비스 가능한 업체의id를 담을 빈 배열 초기화
        List<Long> servicePossibleList = new ArrayList<>();

        // 2. ServiceAreaList의 길이만큼 반복문 실행 +(해당 지역에 서비스를 제공하는 업체들의 수)
        for (Long serviceId : serviceIdList) {

            // 3.해당 업체에 속한 모든 작업자를 검색 후 list에 저장
            List<Worker> workerList = workerRepository.findByServiceId(serviceId);
            // 3-2. 해당 업체에 속한 모든 작업자 수를 int타입의 변수에 저장
            //       ->해당 변수의 값이 0인 업체는 표시되지 않음
            int workerListSize = workerList.size();

                // 4. 해당 작업자가 서비스 가능한 작업자인지 판단하기 위한 반복문
                for (Worker workers : workerList) {
                    // 4-2. 해당 작업자의 id를 가져옴
                    Long workerId = workers.getId();

                    // 4-3. DayOfWeek타입을 int타입으로 변환한뒤 Converter를 통해 최종적으로 Enum타입으로 변환한다.
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    int dayOfWeekInt = dayOfWeek.getValue();

                    DayOfWeekType dayOfWeekType = new DayOfWeekConverter().convertToEntityAttribute(dayOfWeekInt);

                    // 5.해당 날짜의 요일과 작업자의 Id를 통해 작업자의 해당 요일의 업무 일정을 조회
                    Optional<WorkerSchedule> optionalWorkerSchedule = workerScheduleRepository.findByDayOfWeekAndWorkerId(dayOfWeekType, workerId);

                    // 5-2. optionalWorkerSchedule이 비어있으면 해당 반복문 탈출 <-해당 작업자가 해당요일에 휴무이거나 그만둔 작업자이거나 할때 비어있을예정
                    if (optionalWorkerSchedule.isEmpty()) {
                        // 5-3.optonalWorkerSchedule이 비어있으면 해당 작업자는 해당날짜에 서비스 불가한 작업자이므로 반복문 즉시탈출(자신이 속한 반복문만)
                        break;
                    }
                    WorkerSchedule workerSchedules = optionalWorkerSchedule.get();

                    LocalTime startTime = workerSchedules.getServiceStartTime();
                    LocalTime endTime = workerSchedules.getServiceFinishTime();

                    Duration duration = Duration.between(startTime,endTime);
                    List<WorkerReservationHistory> reservationHistoryList = workerReservationHistoryRepository.findByWorkerIdAndReservationDate(workerId,date);

                    for(WorkerReservationHistory reservationHistory : reservationHistoryList){

                        LocalTime reservationStartTime = reservationHistory.getStartTime();
                        LocalTime reservationEndTime = reservationHistory.getEndTime();

                        Duration reservationDuration = Duration.between(reservationStartTime,reservationEndTime);
                        //duration변수의 값을 변경하기위해 Lambda식이 아닌 forEach문 사용
                        duration = duration.minus(reservationDuration);
                    }
                    if(duration.toMinutes() < 60) {
                        workerListSize--;
                        }
                }
                if (workerListSize > 1) {
                    servicePossibleList.add(serviceId);
                    log.info("servicePossibleList : {}", serviceId);
                // 서비스 가능한 작업자가 1명이상 일때
            }

        }
        //컨트롤러로 serviceId 리스트 리턴
        return servicePossibleList;
    }

    @Override
    public List<SearchServiceDataListResponse> findServiceListData(List<Long> serviceIdList,String type) {

        List<SearchServiceDataListResponse> searchServiceDataListResponseList = new ArrayList<>();

        for (Long serviceId : serviceIdList) {
            Optional<Services> serviceOptional = servicesRepository.findById(serviceId);

            if (serviceOptional.isEmpty()) {
                throw new CustomException(ResponseCode.CANNOT_FIND_SERVICE);
            }

            Services service = serviceOptional.get();
            String serviceName = service.getName();
            
            String serviceAddress = service.getAddress();
            String serviceDescription = service.getDescription();

            List<ServiceImage> serviceImageList = serviceImageRepository.findByServiceId(serviceId);
            List<String> searchServiceImgUrlList = new ArrayList<>();

            for (ServiceImage serviceImage : serviceImageList) {
                searchServiceImgUrlList.add(serviceImage.getImgUrl());
            }

            SearchServiceDataListResponse searchServiceDataListResponse = SearchServiceDataListResponse.builder()
                    .name(serviceName)
                    .serviceId(serviceId)
                    .imgUrl(searchServiceImgUrlList)
                    .type(type)
                    .address(serviceAddress)
                    .description(serviceDescription)
                    .type(type)
                    .build();

            searchServiceDataListResponseList.add(searchServiceDataListResponse);
        }

        return searchServiceDataListResponseList;
    }

    @Override //현재 로그인한 유저의 기본주소에 해당하는 업체만 조회되도록
    public List<SearchServiceDataListDto> findServiceTypeSearch(String type) {

        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);

        return serviceCategoryRepository.findByCategoryBaseCategory(serviceType).stream()
                .map(serviceCategory -> SearchServiceDataListDto.builder()
                        .name(serviceCategory.getService().getName())
                        .type(type)
                        .serviceId(serviceCategory.getService().getId())
                        .imgUrl(serviceImageRepository.findByServiceId(serviceCategory.getService().getId()).stream()
                                .map(ServiceImage::getImgUrl)
                                .toList())
                        .address(serviceCategory.getService().getAddress())
                        .description(serviceCategory.getService().getDescription())
                        .build())
                .toList();

    }
    @Override
    public List<ExcellentServiceDto> findExcellentServiceList() {

        List<MostServiceReviewBookmarkCountResponse> listResponse = bookmarkFeignClient.getBestBookmarkServiceIdList();
        List<ExcellentServiceDto> excellentServiceDtoList = new ArrayList<>();

        for (MostServiceReviewBookmarkCountResponse response : listResponse) {
            servicesRepository.findById(response.getServiceId())
                    .ifPresent(service -> {
                        ExcellentServiceDto excellentServiceDto = ExcellentServiceDto.builder()
                                .name(service.getName())
                                .imgUrl(serviceImageRepository.findByServiceId(service.getId()).stream()
                                        .map(ServiceImage::getImgUrl)
                                        .toList())
                                .description(service.getDescription())
                                .address(service.getAddress())
                                .servieId(service.getId())
                                .bookmarkCount(response.getBookmarkCount())
                                .reviewCount(response.getReviewCount())
                                .build();

                        excellentServiceDtoList.add(excellentServiceDto);
                    });
        }

        return excellentServiceDtoList;

    }
}