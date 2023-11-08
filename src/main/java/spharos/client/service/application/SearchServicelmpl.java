package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.service.infrastructure.JPAServiceCategoryRepository;
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServicelmpl implements SearchService{

    private final JPAServiceCategoryRepository serviceCategoryRepository;
    @Override
    public void findServiceList(int typeId){

//        List<ServiceCategory> resultList = serviceCategoryRepository.findServiceIdByCategoryId(typeId);
//
//        log.info("result : {}",resultList);

        Long test = serviceCategoryRepository.findCategoryIdEqualServiceId(typeId);

        log.info("test : {}",test);


    }
}
