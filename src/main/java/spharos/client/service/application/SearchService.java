package spharos.client.service.application;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Long> findSearchResult(String type, LocalDate date, int region) throws ParseException;
}
