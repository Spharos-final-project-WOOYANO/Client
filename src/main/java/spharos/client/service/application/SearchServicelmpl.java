package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.infrastructure.ServiceAreaRepository;
import spharos.client.service.infrastructure.ServiceCategoryRepository;
import spharos.client.service.infrastructure.ServicesRepository;
import spharos.client.service.vo.request.ServiceIdListRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final ServicesRepository servicesRepository;
    @Override
    public void findSearchResult(String type, LocalDate date, int region) {

        //↓ 1. 프론트에서 받아온 지역코드를 통해 해당 지역에 서비스를 제공하는 업체 Id를 검색
        List<Long> serviceIdList = serviceAreaRepository.findServicesIdByAreaCode(region);

        //↓ 2. 업체 카테고리로 다시 serviceIdList를 필터링한다.
        ServiceBaseCategoryType serviceType = new BaseTypeConverter().convertToEntityAttribute(type);

        //↓
        //     for문안의 조건문에서 카테고리와 ServiceId로 Service테이블을 조회하고
        //     조회가 되지 않으면(Repository에서 Optional객체가 리턴되는데 Optional이 empty인 경우)
        //     해당 ServiceId를 serviceIdList에서 제거한다.
        for (Long id: serviceIdList) {
            Optional<Long> typeFilteringId = serviceCategoryRepository.findServiceIdByCategoryBaseCategoryAndServiceId(serviceType,id);
            if(typeFilteringId.isEmpty()){
                serviceIdList.remove(id);
            }
        }

        //↓ 3. 필터링한 List를 vo객체에 대입한다.
        ServiceIdListRequest serviceIdListRequest = new ServiceIdListRequest(serviceIdList);

        //↓ 4. vo객체를 FeignClient 인터페이스의 구현체에 파라미터로 넘기고
        //    해당 서비스의 예약정보를 조회한다.
        //    +파라미터로 받아온 date가 null이면 현재 시간 설정
        if(date != null){
            //
        }

    }

}
