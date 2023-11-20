package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.infrastructure.ServiceAreaRepository;
import spharos.client.service.infrastructure.ServiceCategoryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    @Override
    public void findSearchResult(String type, LocalDate date, int region) {

        //↓ 1. 프론트에서 받아온 지역코드를 통해 해당 지역에 서비스를 제공하는 업체 Id를 검색
        List<Long> serviceIdList = serviceAreaRepository.findServicesIdByAreaCode(region);


        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);
        //↓ 2. 업체 카테고리로 다시 serviceIdList를 필터링한다.
        for (Long id: serviceIdList) {
            Optional<Long> serviceTypeFilter = serviceCategoryRepository.findServiceIdByCategoryBaseCategoryAndServiceId(serviceType,id);
            if(serviceTypeFilter.isPresent()){

            }
        }

    }

}
