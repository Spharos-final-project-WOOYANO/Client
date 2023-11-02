package spharos.client.clients.presentation;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.clients.application.ClientService;
import spharos.client.clients.application.EmailService;
import spharos.client.clients.vo.ClientFindEmailOut;
import spharos.client.clients.vo.ClientSignUpIn;
import spharos.client.global.common.response.BaseResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;
    private final EmailService emailService;

    /*
       입점신청시 이메일 중복체크 후 이메일인증
     */
    @Operation(summary = "입점신청시 이메일 중복체크 후 이메일인증",
            description = "입점신청시 중복되는 이메일이 있는지 확인 후 이메일인증 코드 발송",
            tags = { "Client SignUp" })
    @GetMapping("/email/check")
    public BaseResponse<?> checkEmail(@RequestParam("name") String name,
                                      @RequestParam("email") String email) {

        // 이메일 중복 체크
        clientService.checkEmail(email);

        // 중복체크에 문제가 없으면 인증 메일 전송
        emailService.sendCheckUsableEmail(name, email);

        return new BaseResponse<>();
    }

    /*
        이메일 코드 확인
     */
    @Operation(summary = "이메일 코드 확인",
            description = "이메일 코드 확인",
            tags = { "Client SignUp" })
    @GetMapping("/certnum/check")
    public BaseResponse<?> certifyEmailCode(@RequestParam("email") String email,
                                            @RequestParam("code") String code) {
        // 이메일 코드 확인
        emailService.certifyEmailCode(email, code);
        return new BaseResponse<>();
    }

    /*
        입점신청
     */
    @Operation(summary = "입점신청", description = "입점신청", tags = { "Client SignUp" })
    @PostMapping("/join")
    public BaseResponse<?> join(@RequestBody ClientSignUpIn clientSignUpIn) {
        // 입점신청
        clientService.join(clientSignUpIn);
        return new BaseResponse<>();
    }

    /*
        아이디찾기
     */
    @Operation(summary = "아이디찾기",
            description = "이름과 사업자등록번호로 일치하는 업체의 로그인 아이디(이메일)을 찾음",
            tags = { "Client FindEmail" })
    @GetMapping("/email/find")
    public BaseResponse<?> findEmail(@RequestParam("ceoName") String ceoName,
                                     @RequestParam("registrationNumber") String registrationNumber) {
        // 사업자 아이디 찾기
        ClientFindEmailOut clientFindEmailOut = clientService.findEmail(ceoName, registrationNumber);
        return new BaseResponse<>(clientFindEmailOut);
    }

    /*
        비밀번호 변경시 이메일인증
     */
    @Operation(summary = "비밀번호변경시 이메일 인증",
            description = "비밀번호변경시 이메일인증을 위한 메일 전송",
            tags = { "Client ChangePassword" })
    @GetMapping("/email/password/auth")
    public BaseResponse<?> sendEmailPasswordAuth(@RequestParam("email") String email,
                                                 @RequestParam("registrationNumber") String registrationNumber) {

        // 이메일 존재 체크
        String ceoName = clientService.checkExistEmail(email, registrationNumber);

        // 비밀번호 변경을 위한 인증메일 전송
        emailService.sendPasswordChangeAuthMail(ceoName, email);
        return new BaseResponse<>();
    }

    /*
        비밀번호 변경
     */
    @Operation(summary = "비밀번호변경", description = "비밀번호변경(로그인전)", tags = { "User ChangePassword" })
    @PutMapping("/password")
    public BaseResponse<?> changePassword() {

        return new BaseResponse<>();
    }

    /*
        로그인
     */




}
