package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import java.util.List;
import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

    Optional<ServiceCategory> findByServiceId(Long ServiceId);
    @Query("SELECT sc FROM ServiceCategory sc JOIN sc.category c WHERE c.baseCategory = :baseCategory")
    List<ServiceCategory> findAllByCategoryBaseCategory(@Param("baseCategory") ServiceBaseCategoryType baseCategory);

    List<ServiceCategory> findByCategoryBaseCategory(ServiceBaseCategoryType baseCategory);

}
