package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.service.domain.category.Category;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByBaseCategory(ServiceBaseCategoryType type);

    Optional<Category> findById(Long id);
}
