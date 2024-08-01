package kimjang.toolkit.solsol.domain.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.domain.cache.RefreshToken;
import kimjang.toolkit.solsol.domain.cache.RefreshTokenCacheService;
import kimjang.toolkit.solsol.domain.user.dto.AuthorityInfo;
import kimjang.toolkit.solsol.domain.user.dto.IssuedTokens;
import kimjang.toolkit.solsol.domain.user.dto.RefreshDto;
import kimjang.toolkit.solsol.domain.user.entities.Authority;
import kimjang.toolkit.solsol.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Slf4j
@Component
public class JwtIssuer {
    private final SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
    private final Long ONE_SECONDS = 1000L;
    private final Long ONE_MINUTE = 60 * ONE_SECONDS;
    private final long accessExpireMin;
    private final long refreshExpireMin;
    private final RefreshTokenCacheService refreshTokenCacheService;
    private final PasswordEncoder passwordEncoder;

    public JwtIssuer(@Value("${jwt.access-expire-min:10}") long accessExpireMin,
            @Value("${jwt.refresh-expire-min:60}") long refreshExpireMin,
                     RefreshTokenCacheService refreshTokenCacheService,
                     PasswordEncoder passwordEncoder){
        this.accessExpireMin = accessExpireMin;
        this.refreshExpireMin = refreshExpireMin;
        this.refreshTokenCacheService = refreshTokenCacheService;
        this.passwordEncoder = passwordEncoder;
    }

    public IssuedTokens getAccessTokenByRefreshToken(RefreshDto refreshDto) {
        try {
            AuthorityInfo authorityInfo = getAuthorityInfo(refreshDto);
            String savedRefreshToken = refreshTokenCacheService.searchToken(authorityInfo.getEmail());
            // 저장된 refresh token과 같은지 확인 같을 때 재발급 가능!
            hashMatchingTokens(savedRefreshToken, refreshDto.getRefreshToken());

            return getAccessTokenByAuthorityInfo(authorityInfo);

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

    private void hashMatchingTokens(String savedRefreshToken, String refreshToken){
        if(!passwordEncoder.matches(refreshToken, savedRefreshToken)){
            throw new UnauthorizedException("refresh token is not match");
        }
    }

    private AuthorityInfo getAuthorityInfo(RefreshDto refreshDto) {
        Claims claims = Jwts.parser()
                .verifyWith(key) // 서버가 저장하고 있는 키로 payload 암호 풀기
                .build()
                .parseSignedClaims(refreshDto.getRefreshToken())
                .getPayload();
        String email = (String) claims.get("email");
        String authorities = (String) claims.get("authorities");

        return AuthorityInfo.builder()
                .email(email)
                .authorities(authorities).build();
    }

    public IssuedTokens getAccessTokenByAuthorityInfo(AuthorityInfo authorityInfo) {
        String accessToken = getAccessToken(authorityInfo);
        String refreshToken = getRefreshToken(authorityInfo);
        return IssuedTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getAccessToken(AuthorityInfo authorityInfo) {
        log.info("유통기한 : {}",accessExpireMin*ONE_MINUTE);
        return getToken(authorityInfo.getEmail(), authorityInfo.getAuthorities(), accessExpireMin*ONE_MINUTE);
    }
    public String getRefreshToken(AuthorityInfo authorityInfo) {
        return getToken(authorityInfo.getEmail(), authorityInfo.getAuthorities(), refreshExpireMin*ONE_MINUTE);
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

    public void saveIssuedToken(String email, String refreshToken) {
        String hashedRefreshToken = passwordEncoder.encode(refreshToken);
        refreshTokenCacheService.saveToken(email, hashedRefreshToken);
    }
}
