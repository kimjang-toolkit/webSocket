package kimjang.toolkit.solsol.exception;

import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> exceptionHandler(UnauthorizedException exception){
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> exceptionHandler(BadCredentialsException exception){
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    // 생산된 예외를 처리하여 응답하기ㄴ
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<?> handleAuthenticationException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ JwtInvalidException.class })
    public ResponseEntity<?> handleAuthenticationException(JwtInvalidException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
