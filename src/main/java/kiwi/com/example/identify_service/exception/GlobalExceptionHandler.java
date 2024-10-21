package kiwi.com.example.identify_service.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kiwi.com.example.identify_service.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingException(RuntimeException exception) {
        //        ApiResponse apiResponse = new ApiResponse();
        //
        //        apiResponse.setCode(ErrorCode.UNCATEORIZED_EXCEPTION.getCode());
        //        apiResponse.setMessage(ErrorCode.UNCATEORIZED_EXCEPTION.getMessage());

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(ErrorCode.UNCATEORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEORIZED_EXCEPTION.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    //    @ExceptionHandler(value = RuntimeException.class)
    //    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
    //        ApiResponse apiResponse = new ApiResponse();
    //
    //        apiResponse.setCode(1001);
    //        apiResponse.setMessage(exception.getMessage());
    //
    //        return ResponseEntity.badRequest().body(apiResponse);
    //    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        //        ApiResponse apiResponse = new ApiResponse();
        //
        //        apiResponse.setCode(errorCode.getCode());
        //        apiResponse.setMessage(errorCode.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    //    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    //    ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    //        return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage());
    //    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        //        ApiResponse apiResponse = new ApiResponse<>();
        //
        //        apiResponse.setCode(errorCode.getCode());
        //        apiResponse.setMessage(errorCode.getMessage())
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        ;

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.KEY_INVALID;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e) {
        }

        //        ApiResponse apiResponse = new ApiResponse<>();
        //
        //        apiResponse.setCode(errorCode.getCode());
        //        apiResponse.setMessage(errorCode.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(
                        Objects.nonNull(attributes)
                                ? mapAttribute(errorCode.getMessage(), attributes)
                                : errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
