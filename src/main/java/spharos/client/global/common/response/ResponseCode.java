package spharos.client.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),

    /**
     * 에러 코드
     **/
    NOT_EXISTS_CLIENT_ID(HttpStatus.BAD_REQUEST,false, 2010, "이메일이 존재하지 않습니다."),
    CANNOT_FIND_CLIENT(HttpStatus.BAD_REQUEST, false, 2020,"업체 정보를 찾을 수 없습니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, false, 2030,"아이디 또는 비밀번호를 확인해 주세요."),
    WITHDRAW_CLIENT(HttpStatus.BAD_REQUEST, false, 2040,"탈퇴한 회원입니다."),
    DORMANT_CLIENT(HttpStatus.BAD_REQUEST, false, 2050,"휴면 회원입니다."),
    WAIT_CLIENT(HttpStatus.BAD_REQUEST, false, 2060,"입점 대기중인 회원입니다.");


    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}
