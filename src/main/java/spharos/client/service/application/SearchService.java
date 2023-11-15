package spharos.client.service.application;

import java.time.LocalDate;

public interface SearchService {
    void findSearchResult(String type, LocalDate date, int region);
}
