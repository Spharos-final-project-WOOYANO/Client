package spharos.client.clients.application;

import spharos.client.clients.vo.request.ClientChangePasswordRequest;
import spharos.client.clients.vo.request.ClientLoginRequest;
import spharos.client.clients.vo.request.ClientSignUpRequest;
import spharos.client.clients.vo.response.ClientExistCheckResponse;
import spharos.client.clients.vo.response.ClientFindEmailResponse;
import spharos.client.clients.vo.response.ClientLoginResponse;

public interface ClientService {

    // 이메일 존재 체크
    Boolean checkEmailExist(String email);
    // 입점신청
    void join(ClientSignUpRequest clientSignUpRequest);
    // 아이디찾기
    ClientFindEmailResponse findEmail(String ceoName, String registrationNumber);
    // 비밀번호 변경
    void modifyPassword(ClientChangePasswordRequest clientChangePasswordRequest);
    // 로그인
    ClientLoginResponse login(ClientLoginRequest clientLoginRequest);
    // 사업자 번호와 이메일로 해당하는 업체가 존재하는지 체크
    Boolean checkClientExist(String registrationNumber, String email);

}
