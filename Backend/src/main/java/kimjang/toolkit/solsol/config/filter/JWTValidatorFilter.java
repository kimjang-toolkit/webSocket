package kimjang.toolkit.solsol.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * WebSocket 프로토콜은 Security가 헤더 값을 인식하지 못하기 때문에 넘어간다.
 * not used
 */
@Slf4j
public class JWTValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 특정 헤더로 들어오는 JWT 토큰 값을 추출
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if (null != jwt) { // 토큰이 있는 경우
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                // 토큰의 서명을 통해 유효성을 검사
                Claims claims = Jwts.parser()
                        .verifyWith(key) // 서버가 저장하고 있는 키로 payload 암호 풀기
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                // 토큰 속 payload를 추출
                String email = String.valueOf(claims.get("email"));
                String authorities = (String) claims.get("authorities");
                // payload를 바탕으로 인증 객체를 생성
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                log.info("{}님 인증 됐습니다.",email);
                // 인증 객체를 contextHolder에 저장하기
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // 토큰의 문제가 생길 경우 예외처리
                log.error(e.getMessage(), e);
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 로그인 이외에 API 요청에서 동작한다.
     * 따라서 로그인인 경우 필터를 지나지 않도록 한다.
     * @param request current HTTP request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (request.getServletPath().equals("/user"));
    }

}
