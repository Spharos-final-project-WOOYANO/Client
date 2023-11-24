package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.category.ServiceCategory;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import java.util.List;
import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

    boolean existsByCategoryIdAndServiceId(Long CategoryId, Long ServiceId);
    Optional<ServiceCategory> findByServiceId(Long ServiceId);
    List<ServiceCategory> findByCategoryBaseCategory(ServiceBaseCategoryType baseCategory);

}
