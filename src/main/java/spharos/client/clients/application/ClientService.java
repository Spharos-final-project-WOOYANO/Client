package spharos.client.clients.application;

import spharos.client.clients.vo.*;

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
    ClientLoginOut login(ClientLoginIn clientLoginIn);

}
