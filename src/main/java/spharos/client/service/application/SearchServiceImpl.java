package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.category.Category;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.domain.services.ServiceArea;
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
    private final CategoryRepository categoryRepository;
    private final BookmarkFeignClient bookmarkFeignClient;
    @Override
    public List<Long> findServiceList(String type, LocalDate date, Integer region) {

        // 1. 해당 지역에 서비스를 제공하는 업체들을 조회한다.
        List<ServiceArea> serviceAreaList = serviceAreaRepository.findByAreaCode(region);

        // 1-2. 타입 설정(BaseType설정)
        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);

        // 1-3. date가 null일 경우 현재 날짜로 설정
        if (date == null) {
            date = LocalDate.now();
        }

        // 1-4.서비스 가능한 업체의id를 담을 빈 배열 초기화 -> 어느 시점에서 해야할지 모르겠어서 지금 초기화
        List<Long> servicePossibleList = new ArrayList<>();

        // 2. ServiceAreaList의 길이만큼 반복문 실행 +(해당 지역에 서비스를 제공하는 업체들의 수)
        for (ServiceArea serviceArea : serviceAreaList) {
            //ServiceArea에서 서비스의 id를 가져옴
            Long serviceId = serviceArea.getServices().getId();
            log.info("serviceId : {}", serviceId);

            // 2-2. serviceId와 type을 통해 서비스가 해당 타입의 서비스를 제공하는지 여부를 조회
            //      false - 지역은 일치하지만 타입은 일치하지 않는 업체
            //      true - 해당 지역에서 해당 타입의 서비스를 제공하는 업체
            Optional<Category> checkCategory = categoryRepository.findByBaseCategory(serviceType);
            if (checkCategory.isEmpty()) {
                //↓ 아래의 예외가 실제로 발생할지 의문입니다.
                throw new CustomException(ResponseCode.CANNOT_FIND_SERVICE_CATEGORY_TYPE);
                //  그것이 실제로 발생했습니다....
            }
            boolean checkServiceType = serviceCategoryRepository.existsByCategoryIdAndServiceId(checkCategory.get().getId(), serviceId);

            log.info("serviceTypeFilter : {}", checkServiceType);

            if (checkServiceType) {
                // 3.해당 업체에 속한 모든 작업자를 검색 후 list에 저장
                List<Worker> workerList = workerRepository.findByServiceId(serviceId);

                // 3-2. 해당 업체에 속한 모든 작업자 수를 int타입의 변수에 저장
                //       ->해당 변수의 값이 0인 업체는 표시되지 않음
                int workerListSize = workerList.size();
                log.info("workerListSize : {}", workerListSize);

                // 4. 해당 작업자가 서비스 가능한 작업자인지 판단하기 위한 반복문
                for (Worker workers : workerList) {
                    // 4-2. 해당 작업자의 id를 가져옴
                    Long workerId = workers.getId();

                    // 4-3. DayOfWeek타입을 int타입으로 변환한뒤 Converter를 통해 최종적으로 Enum타입으로 변환한다.
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    int dayOfWeekInt = dayOfWeek.getValue();
                    log.info("dayOfWeekInt : {}", dayOfWeekInt);
                    DayOfWeekType dayOfWeekType = new DayOfWeekConverter().convertToEntityAttribute(dayOfWeekInt);
                    // 5.해당 날짜의 요일과 작업자의 Id를 통해 작업자의 해당 요일의 업무 일정을 조회
                    Optional<WorkerSchedule> optionalWorkerSchedule = workerScheduleRepository.findByDayOfWeekAndWorkerId(dayOfWeekType, workerId);

                    // 5-2. optionalWorkerSchedule이 비어있으면 해당 반복문 탈출 <-해당 작업자가 해당요일에 휴무이거나 그만둔 작업자이거나 할때 비어있을예정
                    if (optionalWorkerSchedule.isEmpty()) {
                        // 5-3.optonalWorkerSchedule이 비어있으면 해당 작업자는 해당날짜에 서비스 불가한 작업자이므로 반복문 즉시탈출(자신이 속한 반복문만)
                        break;
                    }
                    WorkerSchedule workerSchedules = optionalWorkerSchedule.get();
                    log.info("workerSchedules : {}", workerSchedules);

                    // 5-4.해당 근무자의 근무시작시간과 근무종료시간을 LocalTime타입으로 가져옴
                    LocalTime startTime = workerSchedules.getServiceStartTime();
                    LocalTime endTime = workerSchedules.getServiceFinishTime();

                    long diffMinutes = Duration.between(endTime, startTime).toMinutes() / 60;

                    if (diffMinutes < 0) {
                        // + 만약에 근무종료시간이 근무시작시간보다 작은경우가 생긴다면 결과가 음수이므로 양수로 바꿔준다.
                        diffMinutes *= -1;
                    }
                    log.info("diffMinutes : {}", diffMinutes);

                    // 6.해당 작업자의 해당일 예약내역을 List에 저장
                    List<WorkerReservationHistory> workerReservationHistoryList = workerReservationHistoryRepository.findByReservationDateAndWorkerId(date, workers.getId());

                    for (WorkerReservationHistory workerReservationHistory : workerReservationHistoryList) {
                        diffMinutes -= workerReservationHistory.getServiceTime();
                        log.info("diffMinutes : {}", diffMinutes);
                        if (diffMinutes <= 1) {
                            // 위에서 해당 업체의 총 작업자수로 초기화한 변수
                            // 서비스 가능한 작업자수를 저장해서 표시될 업체인지를 판단(0이면 표시 X ,1이상이면 표시될 업체)
                            // 해당 작업자가 서비스 가능시간이 1미만이 되면 서비스 불가능한 작업자이므로 총 작업자수에서 1감소 시킴
                            workerListSize--;
                            log.info("workerListSize : {}", workerListSize);
                        }
                    }
                    log.info("workerListSize : {}", workerListSize);

                    // 서비스 가능한 작업자가 0을 초과할 때
                    if (workerListSize > 0) {
                        servicePossibleList.add(serviceId);
                        log.info("servicePossibleList : {}", serviceId);
                    }
                }
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
                    .address(serviceAddress)
                    .description(serviceDescription)
                    .type(type)
                    .build();

            searchServiceDataListResponseList.add(searchServiceDataListResponse);
        }

        return searchServiceDataListResponseList;
    }

    @Override
    public List<SearchServiceDataListDto> findServiceTypeSearch(String type) {

        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);

        return serviceCategoryRepository.findByCategoryBaseCategory(serviceType).stream()
                .map(serviceCategory -> SearchServiceDataListDto.builder()
                        .name(serviceCategory.getService().getName())
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