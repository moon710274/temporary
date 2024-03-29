package com.seb_main_034.SERVER.response;

import com.seb_main_034.SERVER.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;

    public static ErrorResponse errorResponse(UserException userException) {
        int status = userException.getCode().getStatus();
        String message = userException.getCode().getMessage();

        return new ErrorResponse(status, message);
    }

    public static ErrorResponse verificationErrorResponse(HttpStatus httpStatus) {
        int status = httpStatus.value();
        String message = httpStatus.getReasonPhrase();

        return new ErrorResponse(status, message);
    }

}
