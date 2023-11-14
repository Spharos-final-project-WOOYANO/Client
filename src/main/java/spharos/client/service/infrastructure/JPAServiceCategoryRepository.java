package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.service.domain.ServiceCategory;
import spharos.client.service.domain.serviceCategoryEnum.ServiceBaseCategoryType;

import java.util.List;

public interface JPAServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

//    @Query("SELECT sc.service.id FROM ServiceCategory sc WHERE sc.category.baseCategory = :type")
//    List<Long> findByServiceIdInCategoryBaseType(@Param("type") ServiceBaseCategoryType type);

    List<Long> findByServiceIdInAndCategoryBaseCategory(ServiceBaseCategoryType type,List<Long> serviceIdList);

}
