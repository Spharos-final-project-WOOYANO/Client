package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.dto.SearchServiceDto;
import spharos.client.service.infrastructure.ServiceRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final ServiceRepository serviceRepository;
    @Override
    public List<SearchServiceDto> findServiceList(String serviceType){
        serviceRepository.findServiceList(serviceType);
        return null;
    }
}
