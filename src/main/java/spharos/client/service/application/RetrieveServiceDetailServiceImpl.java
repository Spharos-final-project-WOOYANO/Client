package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;
import spharos.client.clients.infrastructure.ClientServiceListRepository;
import spharos.client.service.domain.services.ServiceArea;
import spharos.client.service.domain.services.ServiceImage;
import spharos.client.service.domain.services.Services;
import spharos.client.service.dto.ServiceDetailDto;
import spharos.client.service.infrastructure.ServiceAreaRepository;
import spharos.client.service.infrastructure.ServiceImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetrieveServiceDetailServiceImpl implements RetrieveServiceDetailService{

    private final ClientServiceListRepository clientServiceListRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final ServiceImageRepository serviceImageRepository;
    @Override
    public ServiceDetailDto retrieveServiceDetail(Long serviceId){

        ClientServiceList clientServiceList = clientServiceListRepository.findByServicesId(serviceId);

        Client client = clientServiceList.getClient();
        Services services = clientServiceList.getServices();


        List<Integer> servicePossibleRegionList = serviceAreaRepository.findByServicesId(serviceId).stream()
                .map(ServiceArea::getAreaCode)
                .toList();

        List<String> imgUrlList = serviceImageRepository.findByServiceId(serviceId).stream()
                .map(ServiceImage::getImgUrl)
                .filter(Objects::nonNull)
                .toList();

        return ServiceDetailDto.builder()
                .description(services.getDescription())
                .serviceAreaList(servicePossibleRegionList)
                .clientName(client.getClientName())
                .clientAddress(client.getClientAddress())
                .registrationNumber(client.getClientRegistrationNumber())
                .serviceImgUrlList(imgUrlList)
                .build();
    }
}
