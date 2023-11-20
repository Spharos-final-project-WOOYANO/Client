package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

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
    @GetMapping("/search") // required=false 옵션으로 해당 requestParam이 null일 경우에도 정상적으로 동작하도록 설정
    public void searchList(@RequestParam("type") String type , @RequestParam(value="date",required=false) LocalDate date , @RequestParam("region") Integer region) throws ParseException {
        log.info("type : {}",type);
        List<Long> possibleServiceIdList = searchService.findSearchResult(type,date,region);
        log.info("Controller-possibleServiceIdList : {}", possibleServiceIdList.size());

    }


}
