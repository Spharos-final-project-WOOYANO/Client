package spharos.client.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.response.ResponseCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

}
