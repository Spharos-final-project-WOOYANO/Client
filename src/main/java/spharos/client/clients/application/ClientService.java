package spharos.client.clients.application;

import spharos.client.clients.vo.ClientSignUpIn;

public interface ClientService {

    // 이메일 중복체크
    void checkEmail(String email);

    // 입점신청
    void join(ClientSignUpIn clientSignUpIn);


}
