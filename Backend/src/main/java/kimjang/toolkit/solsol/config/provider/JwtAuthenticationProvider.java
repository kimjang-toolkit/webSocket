package kimjang.toolkit.solsol.config.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kimjang.toolkit.solsol.config.jwt.JwtAuthenticateToken;
import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * http 헤더에서 JWT를 까고 Authentication 객체를 생성 및 저장한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    /**
     * JwtAuthenticationFilter에서 생성된 token을 받아 인증 진행
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // jwt 복호화할 때 사용하는 key, 암호화할 때 사용한 key와 동일
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        try {
            // jwt token 파싱
            Claims claims; claims = Jwts.parser()
                    .verifyWith(key) // 서버가 저장하고 있는 키로 payload 암호 풀기
                    .build()
                    .parseSignedClaims(((JwtAuthenticateToken) authentication).getJsonWebToken())
                    .getPayload();
            log.info("subject : {}, authorities : {}",claims.getSubject(), getGrantedAuthorities(claims));
            return new JwtAuthenticateToken(claims.get("email"), "", getGrantedAuthorities(claims));
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

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticateToken.class.isAssignableFrom(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        log.info("authorities : {}",claims.get("authorities"));
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
