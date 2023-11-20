package spharos.client.global.feignClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignClientTestService {

    @Autowired
    TestFeignClient testFeignClient;
    public String testService(){
        //↓ test service에서 feign client인터페이스에 정의된 test메서드(?)를 호출
        //  TestFeignClient 인터페이스에 정의해둔 메서드의 getMapping경로로 요청을 보낸다.
        //  테스트 용이기 때문에 다른서비스가 아닌 동일 서비스내에서 진행
        return testFeignClient.testFeign();
    }

}
