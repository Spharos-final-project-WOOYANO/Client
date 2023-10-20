package spharos.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class TestController {

    @GetMapping("/test")
    public String testMethod(){
        return "Client Service";
    }
}
