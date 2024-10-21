package kiwi.com.example.identify_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed!", HttpStatus.NOT_FOUND),
    UNCATEORIZED_EXCEPTION(9999, "Uncategorized exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1003, "Username must be at least 3 characters!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters!", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1004, "Invalid message key!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Unauthorized!", HttpStatus.FORBIDDEN),
    INVALID_DOB(1006, "Your age must be at least {min}", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
