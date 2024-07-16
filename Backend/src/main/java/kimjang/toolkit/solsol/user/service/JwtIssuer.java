package kimjang.toolkit.solsol.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.user.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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

    private String getToken(String email, Set<Authority> authorities, Long expireTime) {
        log.info("authories : {}",populateAuthorities(authorities));
        return Jwts.builder().issuer("Sol-Kimjang").subject("JWT Token")
                // 비밀번호는 절대 안된다.
                .claim("email", email)
                .claim("authorities", populateAuthorities(authorities))
                .issuedAt(new Date())
                // 만료시간, ms 단위, 만료 시간을 넘어선 토큰이 들어오면 만료됐다고 예외가 발생한다.
                .expiration(new Date(expireTime))
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
        log.info("유통기한 : {}",accessExpireMin);
        return getToken(email, authorities, accessExpireMin);
    }
    public String getRefreshToken(String email, Set<Authority> authorities) {
        return getToken(email, authorities, refreshExpireMin);
    }
}
