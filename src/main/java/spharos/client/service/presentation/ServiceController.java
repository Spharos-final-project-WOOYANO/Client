package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.client.service.application.SearchService;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
public class ServiceController {

    private final SearchService searchService;

    @GetMapping("/service")
    public void houseKeeperList(@RequestParam("type") int typeId){

        searchService.findServiceList(typeId);

    }

}
