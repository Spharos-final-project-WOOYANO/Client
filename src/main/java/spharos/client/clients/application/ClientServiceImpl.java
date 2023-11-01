package spharos.client.clients.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spharos.client.clients.domain.Bank;
import spharos.client.clients.domain.Client;
import spharos.client.clients.infrastructure.BankRepository;
import spharos.client.clients.infrastructure.ClientRepository;
import spharos.client.clients.vo.ClientSignUpIn;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;
//    private final AuthenticationManager authenticationManager;

    // 이메일 중복체크 후 이메일인증
    @Override
    public void checkEmail(String email) {

        Optional<Client> client = clientRepository.findByClientId(email);

        // 해당 이메일이 DB에 존재하면 예외처리
        if(client.isPresent()){
            throw new CustomException(ResponseCode.USER_ID_DUPLICATE);
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

}
