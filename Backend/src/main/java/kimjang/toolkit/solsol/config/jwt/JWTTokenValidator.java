package kimjang.toolkit.solsol.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JWTTokenValidator {
        public String getEmail(String jwt){
        if (null != jwt) { // 토큰이 있는 경우
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                // 토큰의 서명을 통해 유효성을 검사
                Claims claims = Jwts.parser()
                        .verifyWith(key) // 서버가 저장하고 있는 키로 payload 해싱
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                // 토큰 속 payload를 추출
                String email = String.valueOf(claims.get("email"));

                return email;
            } catch (Exception e) {
                log.error(e.getMessage());
                // 토큰의 문제가 생길 경우 예외처리
                throw new BadCredentialsException("Invalid Token received!");
            }
        } else{
            log.error("Header don't has any token");
            throw new BadCredentialsException("Header don't has any token");
        }
    }


}
