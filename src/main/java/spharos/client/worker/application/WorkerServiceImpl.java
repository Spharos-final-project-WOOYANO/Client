package spharos.client.worker.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.infrastructure.ServiceCategoryRepository;
import spharos.client.worker.domain.Worker;
import spharos.client.worker.domain.WorkerReservationHistory;
import spharos.client.worker.domain.converter.DayOfWeekConverter;
import spharos.client.worker.domain.enumType.DayOfWeekType;
import spharos.client.worker.dto.ReservationPossibleWorkerDto;
import spharos.client.worker.dto.WorkerDetailDto;
import spharos.client.worker.infrastructure.WorkerRepository;
import spharos.client.worker.infrastructure.WorkerReservationHistoryRepository;
import spharos.client.worker.infrastructure.WorkerScheduleRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService{

    private final WorkerRepository workerRepository;
    private final WorkerReservationHistoryRepository workerReservationHistoryRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    @Override
    public List<WorkerDetailDto> retrieveWorkerList(Long ServiceId) {

        // 해당 서비스 타입 조회 - 작업자는 가사도우미 서비스와 가전제품 청소 서비스에만 존재하므로
        //                      BaseCategory가 1이나 4여야 한다.
        ServiceCategory serviceCategory = serviceCategoryRepository.findByServiceId(ServiceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        String baseType = new BaseTypeConverter().convertToDatabaseColumn(serviceCategory.getCategory().getBaseCategory());
        //서비스 타입을 converter를 통해 변환한뒤 1이나 4가 아니면 예외 발생
        if(Integer.parseInt(baseType) != 1 && Integer.parseInt(baseType) != 4){
            throw new CustomException(ResponseCode.CANNOT_FIND_SERVICE_TYPE_WORKER);
        }
        // 서비스에 해당하는 모든 작업자를 조회해서 List로 저장
        List<Worker> workerList = workerRepository.findByServiceId(ServiceId);

        List<WorkerDetailDto> workerDetailDtoList = new ArrayList<>();

        for (Worker worker: workerList) {

            WorkerDetailDto workerDetailDto = WorkerDetailDto.builder()
                            .workerId(worker.getId())
                            .name(worker.getName())
                            .imgUrl(worker.getImgUrl())
                            .description(worker.getDescription())
                            .build();

            workerDetailDtoList.add(workerDetailDto);
        }

        return workerDetailDtoList;
    }

    @Override
    public ReservationPossibleWorkerDto findPossibleWorker(Long serviceId,Long workerId,LocalDate date) {

        DayOfWeekConverter dayOfWeekConverter = new DayOfWeekConverter();
        DayOfWeekType dayOfWeek = dayOfWeekConverter.convertToEntityAttribute(date.getDayOfWeek().getValue());

        Optional<Worker> workerOptional = workerRepository.findById(workerId);
        String workerName = workerOptional.map(Worker::getName).orElse("");

        //예약 가능한 시간들을 담을 배열 생성
        List<LocalTime> possibleTime = workerScheduleRepository.findByDayOfWeekAndWorkerId(dayOfWeek,workerId).stream()
                .flatMap(workerSchedule -> {

                    LocalTime startTime = workerSchedule.getServiceStartTime();
                    LocalTime endTime = workerSchedule.getServiceFinishTime();

                    //startTime과 endTime의 차이를 구한다.
                    Duration duration = Duration.between(startTime,endTime);

                    List<LocalTime> timeList = new ArrayList<>();
                    //해당 작업자의 출근시간 ~ 퇴근시간까지의 시간을 60분 단위로 timeList에 저장한다.
                    for(int i = 0; i <= duration.toMinutes(); i+=60){
                        timeList.add(startTime.plusMinutes(i));
                    }
                    //예약자의 date에 해당하는 날짜의 예약 기록을 조회한다.
                    List<WorkerReservationHistory> reservationHistoryList = workerReservationHistoryRepository.findByWorkerIdAndReservationDate(workerId,date);

                    // 해당 작업자의 date에 해당하는 날짜의 예약기록의 시작시간과 끝시간을 비교해서
                    reservationHistoryList
                            .forEach(reservationHistory -> {

                                LocalTime reservationStartTime = reservationHistory.getStartTime();
                                LocalTime reservationEndTime = reservationHistory.getEndTime();

                                Duration reservationDuration = Duration.between(reservationStartTime,reservationEndTime);
                                // 시작시간 ~ 종료시간
                                // 시작시간 + i시간 ~ 종료시간
                                for(int y = 0; y <= reservationDuration.toMinutes(); y+= 60){
                                    timeList.remove(reservationStartTime.plusMinutes(y));
                                }
                            });
                    return timeList.stream();
                })
                .toList();

        return ReservationPossibleWorkerDto.builder()
                        .workerId(workerId)
                        .workerName(workerName)
                        .workerPossibleTime(possibleTime)
                        .build();

    }
}
