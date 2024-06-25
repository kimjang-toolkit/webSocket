package kimjang.toolkit.solsol.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationFilter implements Filter {
    public static final String AUTHENTICATION_SCHEME_BASIC = "basic";
    private Charset credentialsCharset = StandardCharsets.UTF_8;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 서블릿 요청과 응답을 HTTP서블릿 요청, 응답으로 수
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header = req.getHeader(AUTHORIZATION); // AUTHORIZATION 이라는 헤더 값 가져오기
        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                // 자격증명의 앞 6글자를 제외하고 나머지 값들을 문자열로 변환
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    // JWT 토큰 디코딩
                    decoded = Base64.getDecoder().decode(base64Token);
                    // 토큰 문자열로 변환
                    String token = new String(decoded, credentialsCharset);
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    // 앞부분인 email
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        } else{
            System.out.println("jwt 토큰이 없습니다.");
        }
        chain.doFilter(request, response);
    }
}
