package spharos.client.service.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.client.service.application.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ServiceController {

    private final SearchService searchService;

    @GetMapping("/service")
    public void houseKeeperController(@RequestParam("type") String serviceType){
        searchService.findServiceList(serviceType);


    }
//    @GetMapping("/moving-clean")
//    public BaseResponse<?> movingCleanController(){
//
//    }
//    @GetMapping("/office-clean")
//    public BaseResponse<?> officeCleanController(){
//
//    }
//    @GetMapping("/electronics-clean")
//    public BaseResponse<?> electronicsCleanController(){
//
//    }
}
