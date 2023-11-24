package spharos.client.service.application;

import spharos.client.service.vo.response.SearchServiceDataListResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Long> findServiceList(String type, LocalDate date, Integer region) throws ParseException;

    List<SearchServiceDataListResponse> findServiceListData(List<Long> serviceIdList,String type);
}
