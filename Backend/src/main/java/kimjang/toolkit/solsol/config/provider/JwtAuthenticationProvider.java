package kimjang.toolkit.solsol.config.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kimjang.toolkit.solsol.config.jwt.JwtAuthenticationToken;
import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.user.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * WebSocket 헤더에서 JWT를 까고
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(key) // 서버가 저장하고 있는 키로 payload 암호 풀기
                    .build()
                    .parseSignedClaims(((JwtAuthenticationToken) authentication).getJsonWebToken())
                    .getPayload();
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException("signature key is different", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }
        return new JwtAuthenticationToken(claims.getSubject(), "", getGrantedAuthorities(claims));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
    }

//    public String isValid(String jwt){
//        if (null != jwt) { // 토큰이 있는 경우
//            try {
//                SecretKey key = Keys.hmacShaKeyFor(
//                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//                // 토큰의 서명을 통해 유효성을 검사
//                Claims claims = Jwts.parser()
//                        .verifyWith(key) // 서버가 저장하고 있는 키로 payload 해싱
//                        .build()
//                        .parseSignedClaims(jwt)
//                        .getPayload();
//                // 토큰 속 payload를 추출
//                String email = String.valueOf(claims.get("email"));
//                String authorities = (String) claims.get("authorities");
//                // payload를 바탕으로 인증 객체를 생성
//                Authentication auth = new UsernamePasswordAuthenticationToken(email, null,
//                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//                System.out.println(email+"님 인증 됐습니다.");
//                // 인증 객체를 contextHolder에 저장하기
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                return email;
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                // 토큰의 문제가 생길 경우 예외처리
//                throw new BadCredentialsException("Invalid Token received!");
//            }
//        } else{
//            log.error("Header don't has any token");
//            throw new BadCredentialsException("Header don't has any token");
//        }
//    }


}
