package spharos.client.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spharos.client.service.application.SearchService;

@SpringBootTest
public class ServiceConverterTest {

    @Autowired
    SearchService searchService;
    @Test
    void findServiceListTest(){
        String testParam = "HOUSE-KEEPER";
        searchService.findServiceList("house-keeper");
        searchService.findServiceList(testParam);
    }


}
