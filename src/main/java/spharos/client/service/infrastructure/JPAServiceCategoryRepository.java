package spharos.client.service.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.client.service.domain.ServiceCategory;
import java.util.List;

public interface JPAServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

    @Query("SELECT sc.service.id FROM ServiceCategory sc WHERE sc.category.id = :typeId")
    Long findCategoryIdEqualServiceId(@Param("typeId") int typeId);

    List<ServiceCategory> findServiceIdByCategoryId(int typeId);
}
