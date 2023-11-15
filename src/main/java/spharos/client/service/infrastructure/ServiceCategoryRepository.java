package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import java.util.List;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Integer> {

//    @Query("SELECT sc.service.id FROM ServiceCategory sc WHERE sc.category.baseCategory = :type")
//    List<Long> findByServiceIdInCategoryBaseType(@Param("type") ServiceBaseCategoryType type);

//    List<Long> findByServiceIdInCategoryBaseCategory(ServiceBaseCategoryType type,List<Long> serviceIdList);

}
