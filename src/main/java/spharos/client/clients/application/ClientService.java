package spharos.client.clients.application;

import spharos.client.clients.vo.*;

public interface ClientService {

    // 이메일 중복체크
    void checkEmail(String email);
    // 입점신청
    void join(ClientSignUpIn clientSignUpIn);
    // 아이디찾기
    ClientFindEmailOut findEmail(String ceoName, String registrationNumber);
    // 이메일 존재 체크
    String checkExistEmail(String email, String registrationNumber);
    // 비밀번호 변경
    void modifyPassword(ClientChangePasswordIn clientChangePasswordIn);
    // 로그인
    ClientLoginOut login(ClientLoginIn clientLoginIn);

}
