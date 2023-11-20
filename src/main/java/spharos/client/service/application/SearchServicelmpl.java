package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.infrastructure.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final WorkerHistoryRepository workerReservationHistory;
    private final WorkerRepository workerRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    @Override
    public void findSearchResult(String type, LocalDate date, int region) throws ParseException {

        //↓ 1. 프론트에서 받아온 지역코드를 통해 해당 지역에 서비스를 제공하는 업체 Id를 검색
        List<Long> serviceIdList = serviceAreaRepository.findServicesIdByAreaCode(region);


        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);
        //↓ 2. 업체 카테고리로 다시 serviceIdList를 필터링한다.
        for (Long id: serviceIdList) {

            // 2-1. serviceId와 type을 통해 일치하는 serviceId를 찾는다.
            Optional<Long> serviceTypeFilter = serviceCategoryRepository.findServiceIdByCategoryBaseCategoryAndServiceId(serviceType,id);

            // 2-2. 일치하는 serviceId가 없으면 조건문 실행안하고 다시 반복문 실행
            if(serviceTypeFilter.isPresent()){

                // 2-3.타입 + 지역이 일치하는 service라면 해당 service에 속한 모든 Worker를 검색
                List<Long> workerList = workerRepository.findIdByServiceId(serviceTypeFilter.get());

                // 2-4. 서비스 가능한 작업자가 있는지 반복문으로 검색함
                for (Long workerId: workerList){

                    List<Integer> workerServiceTime = workerReservationHistory.findServiceTimeByWorkerId(workerId);

                    Optional<LocalTime> OptionalStartTime = workerScheduleRepository.findStartTimeByWorkerId(workerId);
                    Optional<LocalTime> OptionalEndTime = workerScheduleRepository.findEndTimeByWorkerId(workerId);

                    LocalTime startTime = OptionalStartTime.orElse(null);
                    LocalTime endTime = OptionalEndTime.orElse(null);

                    if (startTime == null || endTime == null){
                        //start time이나 end time이 null일경우 반복문을 벗어남
                        continue;
                    }

                    //총 근무시간을 구하기위해 지정 포맷으로 변환
                    Date startTimeFormat = new SimpleDateFormat("HH:mm").parse(startTime.toString());
                    Date endTimeFormat = new SimpleDateFormat("HH:mm").parse(endTime.toString());

                    //시작시간 - 종료시간 = 총 근무시간
                    Long diffHor = (startTimeFormat.getTime() - endTimeFormat.getTime()) / (6000);

                    // 서비스 가능한 작업자수를 저장하는 변수
                    int totalWorkerCounter= 0;

                    // 2-5. 총 근무시간에서 예약된시간들을 뺐을때 1미만이면 예약이 불가능한 작업자
                    for (Integer serviceTime: workerServiceTime){

                        //↓언박싱하려하니 불필요한 언박싱을 하려한다는 경고가 떠서 주석처리
                        //unboxingServiceTime = serviceTime.intValue();
                        diffHor -= serviceTime;
                        if (diffHor <= 1){
                            //총 근무시간 - 예약한 시간을 연산했을때 1미만이 되면 반복문 탈출
                            break;
                        }
                        totalWorkerCounter++;
                    }

                }

            }
        }

    }

}
