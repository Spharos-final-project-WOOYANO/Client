package spharos.client.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spharos.client.global.common.response.BaseResponse;
import spharos.client.global.common.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<?> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }

//    @ExceptionHandler(value = { CustomException.class })
//    public ResponseEntity<BaseResponse> customExHandle(CustomException e) {
//        BaseResponse response = new BaseResponse(e);
//        log.error("[exceptionHandle] ex", e);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
//    }


}
