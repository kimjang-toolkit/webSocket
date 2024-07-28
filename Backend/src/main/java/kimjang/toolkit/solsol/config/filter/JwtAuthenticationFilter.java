package kimjang.toolkit.solsol.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimjang.toolkit.solsol.config.jwt.JwtAuthenticateToken;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Header 속 JWT 값을 Authentication 객체로 관리하는 필터
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    /**
     * JwtAuthenticateToken 값을 인증하도록 Provider에게 요청
     * 인증이 성공하면 SecurityContextHolder에 저장.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 특정 헤더로 들어오는 JWT 토큰 값을 추출
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if(isValid(jwt)){
            try {
                Authentication authentication = authenticationManager.authenticate(new JwtAuthenticateToken(jwt.substring(7)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException authenticationException) {
                SecurityContextHolder.clearContext();
                log.error(authenticationException.getMessage(), authenticationException);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isValid(String jwt) {
        return StringUtils.hasText(jwt) && jwt.startsWith(SecurityConstants.BEARER_PREFIX);
    }
}
