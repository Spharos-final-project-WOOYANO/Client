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
    USER_ID_DUPLICATE(HttpStatus.BAD_REQUEST,false, 2001, "이미 존재하는 이메일입니다."),
    FAILED_SENDING_MAIL(HttpStatus.BAD_REQUEST,false, 2002, "메일 전송에 실패하였습니다."),
    EXPIRATION_MAIL_CODE(HttpStatus.BAD_REQUEST,false, 2003, "만료된 코드이거나 일치하는 이메일 정보가 없습니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST,false, 2004, "인증코드가 일치하지 않습니다."),
    NOT_EXISTS_CLIENT_ID(HttpStatus.BAD_REQUEST,false, 2005, "이메일이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}
