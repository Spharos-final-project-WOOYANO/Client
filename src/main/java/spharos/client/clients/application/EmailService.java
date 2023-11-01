package spharos.client.clients.application;

public interface EmailService {

    // 입점신청시 사용가능한 이메일인지 확인하기 위해 확인 메일 발송
    void sendCheckUsableEmail(String ceoName, String email);

    // 이메일 인증코드 확인
    void certifyEmailCode(String email, String code);

}
