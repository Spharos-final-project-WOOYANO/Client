package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;
import spharos.client.clients.infrastructure.ClientServiceListRepository;
import spharos.client.service.domain.services.ServiceArea;
import spharos.client.service.domain.services.Services;
import spharos.client.service.dto.ServiceDetailDto;
import spharos.client.service.infrastructure.ServiceAreaRepository;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RetrieveServiceDetailServiceImpl implements RetrieveServiceDetailService{

    private final ClientServiceListRepository clientServiceListRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    @Override
    public ServiceDetailDto retrieveServiceDetail(Long serviceId){

        ClientServiceList clientServiceList = clientServiceListRepository.findByServicesId(serviceId);

        Client client = clientServiceList.getClient();
        Services services = clientServiceList.getServices();

        List<ServiceArea> serviceAreas = serviceAreaRepository.findByServicesId(serviceId);

        List<Integer> servicePossibleRegionList = new ArrayList<>();

        for(ServiceArea serviceArea : serviceAreas){
            servicePossibleRegionList.add(serviceArea.getAreaCode());
        }

        ServiceDetailDto serviceDetailDto = ServiceDetailDto.builder()
                .description(services.getDescription())
                .serviceAreaList(servicePossibleRegionList)
                .clientName(client.getClientName())
                .cliendAddress(client.getClientAddress())
                .registrationNumber(client.getClientRegistrationNumber())
                .build();
        return serviceDetailDto;
    }
}
