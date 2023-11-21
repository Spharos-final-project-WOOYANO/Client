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
import spharos.client.clients.domain.Client;
import spharos.client.clients.domain.ClientServiceList;
import spharos.client.clients.dto.ChangePasswordDto;
import spharos.client.clients.dto.ConfirmPasswordDto;
import spharos.client.clients.infrastructure.ClientRepository;
import spharos.client.clients.infrastructure.ClientServiceListRepository;
import spharos.client.clients.vo.request.ClientLoginRequest;
import spharos.client.clients.vo.request.ClientModifyRequest;
import spharos.client.clients.vo.request.ClientSignUpRequest;
import spharos.client.clients.vo.response.ClientFindEmailResponse;
import spharos.client.clients.vo.response.ClientInformationResponse;
import spharos.client.clients.vo.response.ClientLoginResponse;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.config.security.JwtTokenProvider;
import spharos.client.global.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final BankService bankService;
    private final ClientRepository clientRepository;
    private final ClientServiceListRepository clientServiceListRepository;
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
    public void modifyPassword(ChangePasswordDto dto) {

        // 비밀번호를 변경할 업체 확인
        Client client = clientRepository.findByClientId(dto.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 비밀번호 변경
        client.setClientPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
    }

    // 로그인
    @Override
    public ClientLoginResponse login(ClientLoginRequest clientLoginRequest) {

        // 업체 확인
        Client client = clientRepository.findByClientId(clientLoginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_FAIL));

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

        // 아이디와 비밀번호 확인
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        clientLoginRequest.getEmail(),
                        clientLoginRequest.getPassword()
                )
        );

        // 토큰발급
        String accessToken = jwtTokenProvider.generateToken(client);
        // 리프레시 토큰 발급 TODO
//        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        // 서비스 정보 가져오기
        List<ClientServiceList> clientServiceLists = clientServiceListRepository.findByClient(client);

        List<Long> serviceIdList = new ArrayList<>();
        if(!clientServiceLists.isEmpty()) {
            serviceIdList = clientServiceLists.stream()
                    .map(clientServiceList -> clientServiceList.getServices().getId())
                    .toList();
        }

        return ClientLoginResponse.builder()
                .token(accessToken)
                .email(client.getClientId())
                .serviceIdList(serviceIdList)
                .build();
    }

    // 사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크
    @Override
    public Boolean checkClientExist(String registrationNumber, String email) {

        Optional<Client> client = clientRepository.findByClientId(email);

        // 해당 이메일이 DB에 존재하는지 확인
        if(client.isEmpty()) {
            return Boolean.FALSE;
        }

        // 사업자 번호가 일치하는지 확인
        if(!client.get().getClientRegistrationNumber().equals(registrationNumber)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    // 업체 회원 정보 조회
    @Override
    public ClientInformationResponse getClientInformation(String email) {

        // 이메일로 업체 회원 정보 조회
        Client client = clientRepository.findByClientId(email)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        return ClientInformationResponse.builder()
                .clientId(client.getClientId())
                .ceoName(client.getCeoName())
                .clientName(client.getClientName())
                .clientPhone(client.getClientPhone())
                .clientAddress(client.getClientAddress())
                .clientRegistrationNumber(client.getClientRegistrationNumber())
                .createdAt(client.getCreatedAt())
                .build();
    }

    // 업체 회원 정보 수정
    @Override
    @Transactional
    public void modifyClient(String email, ClientModifyRequest request) {

        // 이메일로 업체 회원 정보 조회
        Client client = clientRepository.findByClientId(email)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 업체 정보 수정
        client.modifyClient(request.getCeoName(), request.getClientName(), request.getClientPhone(),
                request.getClientAddress());
    }

    // 비밀번호 확인
    @Override
    public Boolean confirmPassword(ConfirmPasswordDto confirmPasswordDto) {

        // 이메일로 업체 회원 정보 조회
        Client client = clientRepository.findByClientId(confirmPasswordDto.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_CLIENT));

        // 비밀번호 일치 확인
        if(new BCryptPasswordEncoder().matches(confirmPasswordDto.getPassword(),client.getPassword())) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}
