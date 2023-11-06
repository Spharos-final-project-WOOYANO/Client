package spharos.client.bank.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class BankController {

    // 계좌 추가, 수정, 삭제 화면이 생길시 구현 예정

}
