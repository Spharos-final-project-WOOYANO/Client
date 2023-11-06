package spharos.client.service.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import spharos.client.service.domain.QService;
import spharos.client.service.dto.SearchServiceDto;

import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class ServiceRepository implements JPAServicecRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QService service = QService.service;

  @Transactional
  public List<SearchServiceDto> findServiceList(String type){
      jpaQueryFactory.selectFrom(service)
              .where(service.())
  }

}
