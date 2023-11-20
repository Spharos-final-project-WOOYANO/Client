package spharos.client.global.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//feign client 인터페이스
@FeignClient(name = "client-service",url = "http://localhost:8000/api/v1/client")
public interface TestFeignClient {
    @GetMapping("/feign/test")
    String testFeign();
}
