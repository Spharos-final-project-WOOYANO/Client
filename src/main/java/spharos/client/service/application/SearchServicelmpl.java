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

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceAreaRepository serviceAreaRepository;

    @Override
    public void findSearchResult(String type, LocalDate date, int region) {

//        LocalDate now = LocalDate.now();//date가 null일 경우 현재 날짜를 기준으로 검색

        //↓ 1. 프론트에서 받아온 지역코드를 통해 해당 지역에 서비스를 제공하는 업체를 검색
//        List<Long> listOfProvideServicesToRegion = serviceAreaRepository.findServiceIdByAreaCodeIn(region);

        //↓ 2. 업체 카테고리로 다시 serviceIdList를 필터링한다.
//        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);
//        for (Long id:
//             listOfProvideServicesToRegion) {
//
//            id = serviceCategoryRepository.findByServiceIdInCategoryBaseCategory(serviceType,id);
//        }
//        List <Long> serviceIdList = serviceCategoryRepository.findByServiceIdInCategoryBaseCategory(serviceType,listOfProvideServicesToRegion);

        //↓ 3. 검색한 serviceIdList를 통해 해당 날짜에 서비스가능한 업체를 검색한다.



    }

}
