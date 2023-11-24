package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;
import spharos.client.clients.infrastructure.ClientRepository;
import spharos.client.clients.infrastructure.ClientServiceListRepository;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.services.ServiceArea;
import spharos.client.service.domain.services.ServiceImage;
import spharos.client.service.domain.services.Services;
import spharos.client.service.dto.ServiceWorkerScheduleDto;
import spharos.client.service.dto.ServiceWorkerScheduleRegisterDto;
import spharos.client.service.infrastructure.ServiceAreaRepository;
import spharos.client.service.infrastructure.ServiceImageRepository;
import spharos.client.service.infrastructure.ServicesRepository;
import spharos.client.service.vo.request.*;
import spharos.client.service.vo.response.ClientServiceResponse;
import spharos.client.service.vo.response.ServiceAreaResponse;
import spharos.client.service.vo.response.ServiceWorkerListResponse;
import spharos.client.worker.application.WorkerService;
import spharos.client.worker.domain.Worker;
import spharos.client.worker.domain.WorkerSchedule;
import spharos.client.worker.domain.converter.DayOfWeekConverter;
import spharos.client.worker.domain.enumType.DayOfWeekType;
import spharos.client.worker.infrastructure.WorkerRepository;
import spharos.client.worker.infrastructure.WorkerScheduleRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientMypageServiceImpl implements ClientMypageService {

    private final ServicesRepository servicesRepository;
    private final ServiceImageRepository serviceImageRepository;
    private final ClientRepository clientRepository;
    private final ClientServiceListRepository clientServiceListRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final WorkerRepository workerRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    private final WorkerService workerService;

    // 매장정보 조회
    @Override
    public ClientServiceResponse getClientService(Long serviceId) {

        // 서비스 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 서비스 이미지 조회
        List<ServiceImage> serviceImageList = serviceImageRepository.findByService(services);
        List<String> servcieImageUrlList = new ArrayList<>();
        if(!serviceImageList.isEmpty()) {
            servcieImageUrlList = serviceImageList.stream()
                    .map(ServiceImage::getImgUrl)
                    .toList();
        }

        return ClientServiceResponse.builder()
                .serviceId(services.getId())
                .logoUrl(services.getLogoUrl())
                .description(services.getDescription())
                .headerImgUrl(services.getHeaderImgUrl())
                .name(services.getName())
                .address(services.getAddress())
                .serviceImageUrlList(servcieImageUrlList)
                .build();
    }

    // 매장정보 등록
    @Override
    @Transactional
    public Long registerService(String email, ClientRegisterServiceRequest request) {

        // 대상 클라이언트를 조회
        Client client = clientRepository.findByClientId(email)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 서비스테이블에 등록
        Services services = Services.createService(request.getLogoUrl(), request.getDescription(),
                request.getHeaderImgUrl(), request.getName(), request.getAddress());
        servicesRepository.save(services);

        // 서비스 이미지가 있는 경우 테이블에 등록
        if(request.getServiceImageUrlList() != null && !request.getServiceImageUrlList().isEmpty()) {
            for(String url : request.getServiceImageUrlList()) {
                ServiceImage serviceImage = ServiceImage.createServiceImage(url, services);
                serviceImageRepository.save(serviceImage);
            }
        }

        // 클라이언트&서비스 중간테이블에 등록
        ClientServiceList clientServiceList = ClientServiceList.createClientServiceList(services,client);
        clientServiceListRepository.save(clientServiceList);

        return services.getId();
    }

    // 매장정보 수정
    @Override
    @Transactional
    public void modifyService(ClientModifyServiceRequest request) {

        // 수정 대상 서비스 조회
        Services services = servicesRepository.findById(request.getServiceId())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 이미지정보가 있는 경우
        if(request.getServiceImageUrlList() != null && !request.getServiceImageUrlList().isEmpty()) {

            List<ServiceImage> serviceImageList = serviceImageRepository.findByService(services);
            // 기존 이미지 삭제
            for(ServiceImage serviceImage : serviceImageList) {
                serviceImageRepository.delete(serviceImage);
            }

            // 이미지 재등록
            for(String url : request.getServiceImageUrlList()) {
                ServiceImage serviceImage = ServiceImage.createServiceImage(url, services);
                serviceImageRepository.save(serviceImage);
            }
        }

        // 서비스를 수정
        services.modifyService(request.getLogoUrl(), request.getLogoUrl(), request.getHeaderImgUrl(),
                request.getName(), request.getAddress());
    }

    // 서비스 가능 지역 등록
    @Override
    @Transactional
    public void registerServiceArea(ServiceAreaRegisterRequest request) {

        // 서비스 조회
        Services services = servicesRepository.findById(request.getServiceId())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 지역코드 수 만큼 등록
        for(Integer areaCode : request.getAreaCodeList()) {
            ServiceArea serviceArea = ServiceArea.createServiceArea(services, areaCode);
            serviceAreaRepository.save(serviceArea);
        }
    }

    // 서비스 가능 지역 조회
    @Override
    public ServiceAreaResponse getServiceArea(Long serviceId) {

        // 서비스 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 서비스 가능 지역코드를 조회
        List<ServiceArea> serviceAreaList = serviceAreaRepository.findByServices(services);

        List<Integer> areaCodeList = new ArrayList<>();
        if(!serviceAreaList.isEmpty()) {
            areaCodeList = serviceAreaList.stream()
                    .map(ServiceArea::getAreaCode)
                    .toList();
        }

        return ServiceAreaResponse.builder()
                .areaCodeList(areaCodeList)
                .build();
    }

    // 서비스 가능 지역 수정
    @Override
    @Transactional
    public void modifyService(ServiceAreaModifyRequest request) {

        // 서비스 조회
        Services services = servicesRepository.findById(request.getServiceId())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 기존에 등록되어 있는 코드를 삭제
        List<ServiceArea> serviceImageList = serviceAreaRepository.findByServices(services);
        for(ServiceArea serviceArea : serviceImageList) {
            serviceAreaRepository.delete(serviceArea);
        }

        // 새로 코드를 등록
        for(Integer areaCode : request.getAreaCodeList()) {
            ServiceArea serviceArea = ServiceArea.createServiceArea(services, areaCode);
            serviceAreaRepository.save(serviceArea);
        }
    }

    // 작업자 리스트 조회
    @Override
    public List<ServiceWorkerListResponse> getServiceWorkerList(Long serviceId) {

        // 서비스 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 작업자 리스트를 조회
        List<Worker> workerList = workerRepository.findByService(services);

        // 작업자 정보가 없는 경우에는 null을 리턴
        if(workerList.isEmpty()) {
            return null;
        }

        List<ServiceWorkerListResponse> responsesList = new ArrayList<>();
        for(Worker worker : workerList) {

            // 작업자 스케쥴 정보를 조회
            List<WorkerSchedule> workerScheduleList = workerScheduleRepository.findByWorker(worker);
            // 스케쥴정보가 등록되어 있는 경우 dto 작성
            List<ServiceWorkerScheduleDto> scheduleDtoListdtoList = new ArrayList<>();
            if(!workerScheduleList.isEmpty()) {
                for(WorkerSchedule workerSchedule : workerScheduleList) {
                    ServiceWorkerScheduleDto dto = ServiceWorkerScheduleDto.builder()
                            .dayOfWeek(workerSchedule.getDayOfWeek().getKey())
                            .serviceStartTime(workerSchedule.getServiceStartTime())
                            .serviceFinishTime(workerSchedule.getServiceFinishTime())
                            .build();
                    scheduleDtoListdtoList.add(dto);
                }
            }

            ServiceWorkerListResponse response = ServiceWorkerListResponse.builder()
                    .workerId(worker.getId())
                    .imgUrl(worker.getImgUrl())
                    .name(worker.getName())
                    .phone(worker.getPhone())
                    .description(worker.getDescription())
                    .scheduleList(scheduleDtoListdtoList)
                    .build();
            responsesList.add(response);
        }
        return responsesList;
    }

    // 작업자 추가
    @Override
    @Transactional
    public void registerServiceWorker(ServiceWorkerRegisterRequest request) {

        // 서비스 조회
        Services services = servicesRepository.findById(request.getServiceId())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 작업자를 등록
        Worker worker = Worker.createWorker(request.getName(), request.getPhone(), request.getDescription(),
                request.getImgUrl(), Boolean.FALSE, services);
        workerRepository.save(worker);

        // 작업자의 스케쥴 정보가 있는 경우 스케쥴 정보 등록
        if(request.getWorkerScheduleDtoList() != null && !request.getWorkerScheduleDtoList().isEmpty()) {
            for(ServiceWorkerScheduleRegisterDto dto : request.getWorkerScheduleDtoList()) {

                // 요일 변환
                DayOfWeekType dayOfWeekType = new DayOfWeekConverter().convertToEntityAttribute(dto.getDayOfWeek());

                // 숫자값을 시간으로 변환
                LocalTime startTime = LocalTime.of(dto.getServiceStartTime(), 0);
                LocalTime endTime = LocalTime.of(dto.getServiceFinishTime(), 0);

                WorkerSchedule workerSchedule = WorkerSchedule.createWorkerSchedule(services, worker,
                        dayOfWeekType, startTime, endTime);
                workerScheduleRepository.save(workerSchedule);
            }
        }
    }

    // 작업자 삭제
    @Override
    @Transactional
    public void deleteServiceWorker(Long workerId) {

        // 삭제할 작업자를 조회
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_WORKER));

        // 작업자의 스케쥴 정보를 조회
        List<WorkerSchedule> workerScheduleList = workerScheduleRepository.findByWorker(worker);

        // 스케쥴 정보가 있는 경우 삭제
        if(!workerScheduleList.isEmpty()) {
            for(WorkerSchedule workerSchedule : workerScheduleList) {
                workerScheduleRepository.delete(workerSchedule);
            }
        }

        // 작업자 정보를 삭제
        workerRepository.delete(worker);
    }

}
