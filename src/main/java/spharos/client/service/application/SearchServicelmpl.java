package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.domain.converter.BaseTypeConverter;
import spharos.client.service.domain.serviceCategoryEnum.ServiceBaseCategoryType;
import spharos.client.service.infrastructure.JPAServiceCategoryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final JPAServiceCategoryRepository serviceCategoryRepository;
    @Override
    public void findServiceList(String type){

//        List<ServiceCategory> resultList = serviceCategoryRepository.findServiceIdByCategoryId(typeId);
//
        log.info("type : {}",type);

        ServiceBaseCategoryType type2 = new BaseTypeConverter().convertToEntityAttribute(type);
        List<Long> test = serviceCategoryRepository.findCategoryIdEqualServiceId(type2);

        log.info("test : {}",test);


    }
}
