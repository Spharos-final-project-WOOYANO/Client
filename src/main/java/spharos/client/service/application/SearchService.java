package spharos.client.service.application;

import spharos.client.service.dto.SearchServiceDto;

import java.util.List;

public interface SearchService {
    List<SearchServiceDto> findServiceList(String serviceType);
}
