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
import spharos.client.service.infrastructure.ServiceAreaRepository;
import spharos.client.service.infrastructure.ServiceImageRepository;
import spharos.client.service.infrastructure.ServicesRepository;
import spharos.client.service.vo.request.ClientModifyServiceRequest;
import spharos.client.service.vo.request.ClientRegisterServiceRequest;
import spharos.client.service.vo.request.ServiceAreaModifyRequest;
import spharos.client.service.vo.request.ServiceAreaRegisterRequest;
import spharos.client.service.vo.response.ClientServiceResponse;
import spharos.client.service.vo.response.ServiceAreaResponse;

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

}
