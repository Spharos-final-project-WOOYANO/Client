package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

//    @Query("SELECT sc.service.id FROM ServiceCategory sc WHERE sc.category.baseCategory = :type")
//    List<Long> findByServiceIdInCategoryBaseType(@Param("type") ServiceBaseCategoryType type);

    boolean existsByCategoryIdAndServiceId(Long CategoryId, Long ServiceId);

}
