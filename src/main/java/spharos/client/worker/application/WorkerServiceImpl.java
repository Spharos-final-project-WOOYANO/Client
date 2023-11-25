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
import spharos.client.worker.dto.WorkerDetailDto;
import spharos.client.worker.infrastructure.WorkerRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService{

    private final WorkerRepository workerRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
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
}
