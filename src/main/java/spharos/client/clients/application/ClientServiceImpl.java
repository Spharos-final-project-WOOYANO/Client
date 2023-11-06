package spharos.client.clients.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.client.bank.application.BankService;
import spharos.client.bank.dto.BankRegisterDto;
import spharos.client.bank.infrastructure.BankRepository;
import spharos.client.clients.domain.Client;
import spharos.client.clients.infrastructure.ClientRepository;
import spharos.client.clients.vo.*;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.config.security.JwtTokenProvider;
import spharos.client.global.exception.CustomException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final BankService bankService;
    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 이메일 존재 체크
    @Override
    public Boolean checkEmailExist(String email) {

        Optional<Client> client = clientRepository.findByClientId(email);

        // 해당 이메일이 DB에 존재하는지 확인
        if(client.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    // 입점신청
    @Override
    public void join(ClientSignUpRequest request) {

        // 비밀번호 암호화
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        // 업체 등록
        Client client = Client.createClient(request.getEmail(), hashedPassword, request.getCeoName(),
                request.getClientName(), request.getClientPhone(), request.getClientAddress(),
                request.getClientRegistrationNumber(), request.getClientRegistrationImgUrl(), 3);
        clientRepository.save(client);

        // 은행정보 등록
        BankRegisterDto bankRegisterDto = BankRegisterDto.builder()
                .bankName(request.getBankName())
                .bankAccount(request.getBankAccount())
                .bankHolder(request.getBankHolder())
                .bankState(Boolean.TRUE)
                .bankImgUrl(request.getBankImgUrl())
                .client(client)
                .build();
        bankService.saveBank(bankRegisterDto);
    }

    // 아이디찾기
    @Override
    public ClientFindEmailResponse findEmail(String ceoName, String registrationNumber) {

        // 사업자번호로 일치하는 업체가 있는지 조회
        Client client = clientRepository.findByClientRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID));

        // 대표자명이 다른 경우
        if(!client.getCeoName().equals(ceoName)) {
            throw new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID);
        }

        // 사업자 상태가 탈퇴 또는 대기 인 경우
        if(client.getClientStatus() == 1 || client.getClientStatus() == 3) {
            throw new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID);
        }

        return ClientFindEmailResponse.builder()
                .email(client.getClientId())
                .build();
    }


    // 비밀번호 변경
    @Override
    @Transactional
    public void modifyPassword(ClientChangePasswordRequest request) {

        // 비밀번호를 변경할 업체 확인
        Client client = clientRepository.findByClientId(request.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 비밀번호 변경
        client.setClientPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    }





    // 로그인
    @Override
    public ClientLoginOut login(ClientLoginIn clientLoginIn) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        clientLoginIn.getEmail(),
                        clientLoginIn.getPassword()
                )
        );

        // 업체 확인
        Client client = clientRepository.findByClientId(clientLoginIn.getEmail()).get();
//                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_FAIL)); // TODO 발생할 수 없는 예외

        // TODO 시큐리티 내부에서 해야 할 일
        // 업체 상태 확인
        if(client.getClientStatus() == 1) {
            // 탈퇴 상태인 경우
            throw new CustomException(ResponseCode.WITHDRAW_CLIENT);
        } else if(client.getClientStatus() == 2) {
            // 휴면 상태인 경우
            throw new CustomException(ResponseCode.DORMANT_CLIENT);
        } else if(client.getClientStatus() == 3) {
            // 대기 상태인 경우
            throw new CustomException(ResponseCode.WAIT_CLIENT);
        }

        // 토큰발급
        String accessToken = jwtTokenProvider.generateToken(client);
        // 리프레시 토큰 발급 TODO
//        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return ClientLoginOut.builder()
                .token(accessToken)
                .build();
    }


}
