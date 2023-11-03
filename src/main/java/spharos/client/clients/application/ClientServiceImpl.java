package spharos.client.clients.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.client.clients.domain.Bank;
import spharos.client.clients.domain.Client;
import spharos.client.clients.infrastructure.BankRepository;
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

    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 이메일 중복체크 후 이메일인증
    @Override
    public void checkEmail(String email) {

        Optional<Client> client = clientRepository.findByClientId(email);

        // 해당 이메일이 DB에 존재하면 예외처리
        if(client.isPresent()){
            throw new CustomException(ResponseCode.CLIENT_ID_DUPLICATE);
        }
    }

    // 입점신청
    @Override
    public void join(ClientSignUpIn clientSignUpIn) {

        // 비밀번호 암호화
        String hashedPassword = new BCryptPasswordEncoder().encode(clientSignUpIn.getPassword());

        // 업체 등록
        Client client = Client.builder()
                .clientId(clientSignUpIn.getEmail())
                .clientPassword(hashedPassword)
                .ceoName(clientSignUpIn.getCeoName())
                .clientName(clientSignUpIn.getClientName())
                .clientPhone(clientSignUpIn.getClientPhone())
                .clientAddress(clientSignUpIn.getClientAddress())
                .clientRegistrationNumber(clientSignUpIn.getClientRegistrationNumber())
                .clientRegistrationImgUrl(clientSignUpIn.getClientRegistrationImgUrl())
                .clientStatus(3) // 대기
                .build();
        clientRepository.save(client);

        // 은행정보 등록
        Bank bank = Bank.builder()
                .bankName(clientSignUpIn.getBankName())
                .bankAccount(clientSignUpIn.getBankAccount())
                .bankHolder(clientSignUpIn.getBankHolder())
                .bankState(Boolean.TRUE)
                .bankImgUrl(clientSignUpIn.getBankImgUrl())
                .client(client)
                .build();
        bankRepository.save(bank);
    }

    // 아이디찾기
    @Override
    public ClientFindEmailOut findEmail(String ceoName, String registrationNumber) {

        // 대표자명과 사업자번호로 일치하는 업체가 있는지 조회
        Client client = clientRepository.findByCeoNameAndClientRegistrationNumber(ceoName,registrationNumber)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID));

        // 사업자 상태가 탈퇴 또는 대기 인 경우
        if(client.getClientStatus() == 1 || client.getClientStatus() == 3) {
            throw new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID);
        }

        return ClientFindEmailOut.builder()
                .clientId(client.getClientId())
                .build();
    }

    // 이메일 존재 체크
    @Override
    public String checkExistEmail(String email, String registrationNumber) {

        // 이메일과 사업자 번호로 일치하는 업체가 있는지 조회
        Client client = clientRepository.findByClientIdAndClientRegistrationNumber(email,registrationNumber)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_EXISTS_CLIENT_ID));

        // 존재하는 이메일이면 메일 발송을 위해 사업자 이름을 리턴함
        return client.getCeoName();
    }

    // 비밀번호 변경
    @Override
    @Transactional
    public void modifyPassword(ClientChangePasswordIn clientChangePasswordIn) {

        // 비밀번호를 변경할 업체 확인
        Client client = clientRepository.findByClientId(clientChangePasswordIn.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 비밀번호 변경
        client.setClientPassword(new BCryptPasswordEncoder().encode(clientChangePasswordIn.getPassword()));
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
