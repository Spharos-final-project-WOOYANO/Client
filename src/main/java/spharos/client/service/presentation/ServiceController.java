package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
public class ServiceController {

    private final SearchService searchService;

//    @GetMapping("/service")
//    public void houseKeeperList(@RequestParam("type") String type){
//        log.info("type : {}",type);
//        searchService.findServiceList(type);
//
//    }
    @GetMapping("/search")
    public void searchList(@RequestParam("type") String type , @RequestParam("date") LocalDate date , @RequestParam("region") int region) throws ParseException {

        List<Long> possibleServiceIdList = searchService.findSearchResult(type,date,region);

    }

}
