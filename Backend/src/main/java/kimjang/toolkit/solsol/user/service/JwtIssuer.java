package kimjang.toolkit.solsol.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.user.entities.Authority;
import kimjang.toolkit.solsol.user.dto.LoginSuccessDto;
import kimjang.toolkit.solsol.user.dto.RefreshDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class JwtIssuer {
    private final SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
    private final Long ONE_SECONDS = 1000L;
    private final Long ONE_MINUTE = 60 * ONE_SECONDS;
    private final long accessExpireMin;
    private final long refreshExpireMin;
    public JwtIssuer(@Value("${jwt.access-expire-min:10}") long accessExpireMin,
            @Value("${jwt.refresh-expire-min:60}") long refreshExpireMin){
        this.accessExpireMin = accessExpireMin;
        this.refreshExpireMin = refreshExpireMin;
    }

    private String getToken(String email, String authorities, Long expireTime) {
        log.info("authories : {}",authorities);
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Jwts.builder().issuer("Sol-Kimjang").subject("JWT Token")
                // 비밀번호는 절대 안된다.
                .claim("email", email)
                .claim("authorities", authorities)
                .issuedAt(new Date(now))
                // 만료시간, ms 단위, 만료 시간을 넘어선 토큰이 들어오면 만료됐다고 예외가 발생한다.
                .expiration(new Date(now + expireTime))
                // 디지털 서명 작성
                .signWith(key).compact();
    }

    private String populateAuthorities(Set<Authority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (Authority authority : collection) {
            authoritiesSet.add(authority.getName());
        }
        return String.join(",", authoritiesSet);
    }

    public String getAccessToken(String email, Set<Authority> authorities) {
        log.info("유통기한 : {}",accessExpireMin*ONE_MINUTE);
        return getToken(email, populateAuthorities(authorities), accessExpireMin*ONE_MINUTE);
    }
    public String getRefreshToken(String email, Set<Authority> authorities) {
        return getToken(email, populateAuthorities(authorities), refreshExpireMin*ONE_MINUTE);
    }
    public String getAccessToken(String email, String authorities) {
        log.info("유통기한 : {}",accessExpireMin*ONE_MINUTE);
        return getToken(email, authorities, accessExpireMin*ONE_MINUTE);
    }
    public String getRefreshToken(String email, String authorities) {
        return getToken(email, authorities, refreshExpireMin*ONE_MINUTE);
    }

    public LoginSuccessDto getAccessTokenByRefreshToken(RefreshDto refreshDto) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key) // 서버가 저장하고 있는 키로 payload 암호 풀기
                    .build()
                    .parseSignedClaims(refreshDto.getRefreshToken())
                    .getPayload();
            String email = (String) claims.get("email");
            String authorities = (String) claims.get("authorities");

            String accessToken = getAccessToken(email, authorities);
            String refreshToken = getRefreshToken(email, authorities);
            return LoginSuccessDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException("signature key is different", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }

    }
}
