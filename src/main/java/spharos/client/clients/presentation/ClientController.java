package spharos.client.clients.presentation;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.client.clients.application.ClientService;
import spharos.client.clients.dto.ChangePasswordDto;
import spharos.client.clients.dto.ConfirmPasswordDto;
import spharos.client.clients.vo.request.*;
import spharos.client.clients.vo.response.*;
import spharos.client.global.common.response.BaseResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    /*
       이메일 존재 체크
     */
    @Operation(summary = "이메일 존재 체크",
            description = "해당 이메일이 데이터베이스에 존재하는지 체크",
            tags = { "Client SignUp" })
    @GetMapping("/email/check")
    public BaseResponse<?> checkEmailExist(@RequestParam("email") String email) {

        // 이메일이 존재하는지 체크
        Boolean result = clientService.checkEmailExist(email);

        ClientEmailCheckResponse clientEmailCheckResponse = ClientEmailCheckResponse.builder()
                .checkResult(result)
                .build();

        return new BaseResponse<>(clientEmailCheckResponse);
    }

    /*
        입점신청
     */
    @Operation(summary = "입점신청", description = "입점신청", tags = { "Client SignUp" })
    @PostMapping("/join")
    public BaseResponse<?> join(@RequestBody ClientSignUpRequest clientSignUpRequest) {
        // 입점신청
        clientService.join(clientSignUpRequest);
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
        // 아이디 찾기
        ClientFindEmailResponse clientFindEmailResponse = clientService.findEmail(ceoName, registrationNumber);
        return new BaseResponse<>(clientFindEmailResponse);
    }

    /*
        비밀번호 변경
     */
    @Operation(summary = "비밀번호변경", description = "비밀번호변경(로그인전)", tags = { "Client ChangePassword" })
    @PutMapping("/password")
    public BaseResponse<?> changePassword(@RequestBody ClientChangePasswordRequest request) {

        ChangePasswordDto dto = ChangePasswordDto.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        // 비밀번호 변경
        clientService.modifyPassword(dto);
        return new BaseResponse<>();
    }

    /*
        로그인
     */
    @Operation(summary = "로그인", description = "로그인", tags = { "Client Login" })
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody ClientLoginRequest clientLoginRequest) {

        // 로그인
        ClientLoginResponse clientLoginResponse = clientService.login(clientLoginRequest);
        return new BaseResponse<>(clientLoginResponse);
    }

    /*
        사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크
     */
    @Operation(summary = "사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크",
            description = "사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크",
            tags = { "Client ChangePassword" })
    @GetMapping("/exist/check")
    public BaseResponse<?> checkClientExist(@RequestParam("registrationNumber") String registrationNumber,
                                     @RequestParam("email") String email) {

        // 사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크
        Boolean checkResult = clientService.checkClientExist(registrationNumber, email);

        ClientExistCheckResponse response = ClientExistCheckResponse.builder()
                .checkResult(checkResult)
                .build();
        return new BaseResponse<>(response);
    }

    /*
        업체 회원 정보 조회
     */
    @Operation(summary = "업체 회원 정보 조회",
            description = "업체 회원 정보 조회",
            tags = { "Client Mypage" })
    @GetMapping("")
    public BaseResponse<?> getClient(@RequestHeader("email") String email) {

        // 업체 회원 정보 조회
        ClientInformationResponse response = clientService.getClientInformation(email);
        return new BaseResponse<>(response);
    }

    /*
        업체 회원 정보 수정
     */
    @Operation(summary = "업체 회원 정보 수정",
            description = "업체 회원 정보 수정",
            tags = { "Client Mypage" })
    @PutMapping("")
    public BaseResponse<?> modifyClient(@RequestHeader("email") String email,
                                        @RequestBody ClientModifyRequest request) {
        // 업체 회원 정보 수정
        clientService.modifyClient(email, request);
        return new BaseResponse<>();
    }

    /*
        비밀번호 확인
    */
    @Operation(summary = "비밀번호 확인",
            description = "업체 회원정보 관리 페이지에서 비밀번호 변경 전 비밀번호 확인",
            tags = { "Client Mypage" })
    @PostMapping("/mypage/confirm/password")
    public BaseResponse<?> confirmPassword(@RequestHeader("email") String email,
                                        @RequestBody ClientConfirmPasswordRequest request) {

        ConfirmPasswordDto dto = ConfirmPasswordDto.builder()
                .email(email)
                .password(request.getPassword())
                .build();

        // 비밀번호 확인
        Boolean checkResult = clientService.confirmPassword(dto);

        ClientConfirmPasswordResponse response = ClientConfirmPasswordResponse.builder()
                .checkResult(checkResult)
                .build();
        return new BaseResponse<>(response);
    }

    /*
        비밀번호 변경 - 마이페이지
     */
    @Operation(summary = "비밀번호 변경",
            description = "비밀번호 변경",
            tags = { "Client Mypage" })
    @PutMapping("/mypage/password")
    public BaseResponse<?> modifyPassword(@RequestHeader("email") String email,
                                          @RequestBody ClientMypageChangePasswordRequest request) {

        ChangePasswordDto dto = ChangePasswordDto.builder()
                .email(email)
                .password(request.getPassword())
                .build();

        // 비밀번호 변경
        clientService.modifyPassword(dto);
        return new BaseResponse<>();
    }

}
