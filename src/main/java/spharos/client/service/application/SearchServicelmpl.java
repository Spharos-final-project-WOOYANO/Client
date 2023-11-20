package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.infrastructure.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final WorkerHistoryRepository workerHistoryRepository;
    private final WorkerRepository workerRepository;
    private final WorkerScheduleRepository workerScheduleRepository;

    public List<Long> findSearchResult(String type, LocalDate date, int region) throws ParseException {

        //↓ 1. 프론트에서 받아온 지역코드를 통해 해당 지역에 서비스를 제공하는 업체 Id를 검색
        List<Long> serviceIdList = serviceAreaRepository.findServicesIdByAreaCode(region);

        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);
        //이래도 되는지는 모르겠지만 일단 null로 초기화
        List<Long> servicePossibleList = null;

        //↓ 2. 업체 카테고리로 다시 serviceIdList를 필터링한다.
        for (Long id: serviceIdList) {

            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // 2-1. serviceId와 type을 통해 해당지역에서 해당하는 타입의 서비스를 제공하는 serviceId를 찾는다.
            Optional<Long> serviceTypeFilter = serviceCategoryRepository.findServiceIdByCategoryBaseCategoryAndServiceId(serviceType,id);


            // 2-2. 해당 지역에서 해당 타입의 서비스를 제공하는 업체라면 조건문 실행
            if(serviceTypeFilter.isPresent()){

                // 2-3.해당 업체에 속한 모든 작업자의 id를 검색 후 list에 저장
                List<Long> workerList = workerRepository.findIdByServiceId(serviceTypeFilter.get());

                // ↓ 해당 업체에 속한 모든 작업자 수로 초기화함
                int workerListSize = workerList.size();

                // 2-4. 해당 작업자가 서비스 가능한 작업자인지 판단하기 위한 반복문
                for (Long workerId : workerList){

                    //그날 '업무'를 시작하는(출근)시간과 종료(퇴근)시간을 각각 조회해서 wrapper클래스타입 변수에 할당
                    Optional<LocalTime> OptionalStartTime = workerScheduleRepository.findStartTimeByDayOfWeekAndWorkerId(dayOfWeek, workerId);
                    Optional<LocalTime> OptionalEndTime = workerScheduleRepository.findEndTimeByDayOfWeekAndWorkerId(dayOfWeek, workerId);

                    //업무 시작 시간이나 업무 종료 시간이 empty일경우 null로 변환
                    LocalTime startTime = OptionalStartTime.orElse(null);
                    LocalTime endTime = OptionalEndTime.orElse(null);

                    if (startTime == null || endTime == null){
                        //start time이나 end time이 null일경우 반복문을 벗어남
                        continue;
                    }

                    //총 근무시간을 구하기위해 지정 포맷으로 변환
                    Date startTimeFormat = new SimpleDateFormat("HH:mm").parse(startTime.toString());
                    Date endTimeFormat = new SimpleDateFormat("HH:mm").parse(endTime.toString());

                    //업무 시작하는 시간 - 업무 종료시간 = 총 근무시간
                    Long diffHor = (startTimeFormat.getTime() - endTimeFormat.getTime()) / (6000);

                    //예약 시간들을 list에 담아서 저장함(ex - [2,3,2....] )
                    List<Integer> workerServiceTime = workerHistoryRepository.findServiceTimeByReservationDateAndWorkerId(date,workerId);

                    // 2-5. 총 근무시간에서 예약된 시간들을 뺐을때 1미만이면 예약이 불가능한 작업자이고
                    //      서비스 시간들을 list형태로 담아서 저장하기 때문에 for:each문 실행
                    for (Integer serviceTime: workerServiceTime){

                        //↓언박싱하려하니 불필요한 언박싱을 하려한다는 경고가 떠서 주석처리
                        //unboxingServiceTime = serviceTime.intValue();
                        diffHor -= serviceTime;
                        if (diffHor <= 1){
                            //↓위에서 해당 업체의 총 작업자수로 초기화한 변수
                            // 서비스 가능한 작업자수를 저장해서 표시될 업체인지를 판단(0이면 표시 X ,1이상이면 표시될 업체)
                            // 해당 작업자가 서비스 가능시간이 1미만이 되면 서비스 불가능한 작업자이므로 총 작업자수에서 1감소 시킴
                            workerListSize--;
                            break;
                        }

                    }

                }
                if (workerListSize > 0){
                    servicePossibleList.add(id);
                    log.info("servicePossibleList : {}",servicePossibleList.get(0));
                    log.info("servicePossibleList : {}",servicePossibleList.get(1));
                }
            }
        }
        return servicePossibleList;
    }

    //※ 보완하고 싶은점들
    //  1- 서비스 가능한 작업자가 1이상이기만 하다면 해당 업체는 서비스 가능 업체이므로 더 이상의 코드 실행없이(반복문 등)
    //      바로 서비스 가능 업체 목록에 추가하고 싶은데 방법을 모르겠음
    //      + totalWorkerCounter++가 되는 시점에서 해당업체는 표시되어야 하는 업체에 들어가야함
    //  2- 람다등등의 코드길이를 줄이는 방법이나 로직등이 있을거같은데 검색한다고 시간다 보낼까봐
    //     무작정 코드로 작성해서 코드길이가 길어져서 아쉬움
}
