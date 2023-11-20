package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.domain.services.ServiceArea;
import spharos.client.service.domain.worker.Worker;
import spharos.client.service.domain.worker.WorkerReservationHistory;
import spharos.client.service.domain.worker.WorkerSchedule;
import spharos.client.service.domain.worker.converter.DayOfWeekConverter;
import spharos.client.service.domain.worker.enumType.DayOfWeekType;
import spharos.client.service.infrastructure.*;

import java.text.ParseException;
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
    private final WorkerHistoryRepository workerHistoryRepository;
    private final WorkerRepository workerRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    @Override
    public List<Long> findSearchResult(String type, LocalDate date, Integer region) throws ParseException {

        // 1. 프론트에서 받아온 지역코드를 통해 해당 지역코드를 포함한 모든 ServiceArea테이블을 검색함
        List<ServiceArea> serviceAreaList = serviceAreaRepository.findByAreaCode(region);

        // 1-2. 타입 설정
        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);

        // 1-3. date가 null일 경우 현재 날짜로 초기화
        if (date == null) {
            date = LocalDate.now();
        }

        // 빈 배열로 초기화
        List<Long> servicePossibleList = new ArrayList<>();

        // 2. 조회한 List수 만큼 반복
        for (ServiceArea serviceArea : serviceAreaList) {
            Long serviceId = serviceArea.getServices().getId();
            log.info("serviceId : {}", serviceId);

            // 2-1. serviceId와 type을 통해 해당지역에서 해당하는 타입의 서비스를 제공하는 service가 있는지 조회함
            boolean checkServiceType = serviceCategoryRepository.existsByCategoryBaseCategoryAndServiceId(serviceType, serviceId);

            log.info("serviceTypeFilter : {}", checkServiceType);

            // 2-2. 해당 지역에서 해당 타입의 서비스를 제공하는 업체라면 조건문 실행
            if (checkServiceType) {
                // 2-3.해당 업체에 속한 모든 작업자의 id를 검색 후 list에 저장
                List<Worker> workerList = workerRepository.findByServiceId(serviceId);

                // 해당 업체에 속한 모든 작업자 수로 초기화
                int workerListSize = workerList.size();
                log.info("workerListSize : {}", workerListSize);

                // 2-4. 해당 작업자가 서비스 가능한 작업자인지 판단하기 위한 반복문
                for (Worker workers : workerList) {
                    Long workerId = workers.getId();

                    // date의 요일을 DayOfWeek타입에서 -> int -> String으로 변환
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    int dayOfWeekInt = dayOfWeek.getValue();

                    DayOfWeekType dayOfWeekType = new DayOfWeekConverter().convertToEntityAttribute(dayOfWeekInt);
                    Optional<WorkerSchedule> optionalWorkerSchedule = workerScheduleRepository.findByDayOfWeekAndWorkerId(dayOfWeekType, workerId);

                    // optionalWorkerSchedule이 비어있으면 해당 반복문 탈출
                    if(optionalWorkerSchedule.isEmpty()) {
                        break;
                    }
                    WorkerSchedule workerSchedules = optionalWorkerSchedule.get();
                    log.info("workerSchedules : {}", workerSchedules);

                    // 해당 근무자의 근무시작시간과 근무종료시간을 가져옴
                    LocalTime startTime = workerSchedules.getServiceStartTime();
                    LocalTime endTime = workerSchedules.getServiceFinishTime();

                    long diffMinutes = Duration.between(endTime, startTime).toMinutes() / 60;

                    if (diffMinutes < 0) {
                        // 만약에 근무종료시간이 근무시작시간보다 작다면 음수가 나오므로 양수로 바꿔줌
                        diffMinutes *= -1;
                    }
                    log.info("diffMinutes : {}", diffMinutes);

                    // WorkerReservationHistory날짜와 작업자의 아이디를 통해 작업자의 해당날짜의 예약일정을 모두 조회한다.
                    List<WorkerReservationHistory> workerReservationHistoryList = workerHistoryRepository.findByReservationDateAndWorkerId(date, workers.getId());

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
        return servicePossibleList;
    }
}
