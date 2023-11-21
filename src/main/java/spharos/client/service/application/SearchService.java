package spharos.client.service.application;

import spharos.client.service.vo.response.SearchServiceDateListResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Long> findServiceList(String type, LocalDate date, Integer region) throws ParseException;

    List<SearchServiceDateListResponse> findServiceListData(List<Long> serviceIdList);
}
