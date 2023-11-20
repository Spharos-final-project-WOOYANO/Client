package spharos.client.global.feignClient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class TestController {

    private final FeignClientTestService feignClientTestService;

    @GetMapping("/feign")
    public String test(){
        return feignClientTestService.testService();
    }
    //↓ FeignClientTestService에서 보낸 요청에 의해 해당 컨트롤러가 실행됨
    @GetMapping("/feign/test")
    public String testFeign(){
        return "success!";
    }
}
