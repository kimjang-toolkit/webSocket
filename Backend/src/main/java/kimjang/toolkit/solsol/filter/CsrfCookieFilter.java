package kimjang.toolkit.solsol.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 이용 가능한 Csrf 토큰을 찾는다.
        System.out.println("헤더 이름 : "+CsrfToken.class.getName());
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        System.out.println(.);
        System.out.println("CSRF 토큰 받았음 : "+ csrfToken.getToken() +" 이름 : "+csrfToken.getHeaderName());
        if(csrfToken != null){
//            System.out.println("CSRF 토큰 받았음 : "+ csrfToken.getToken());
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getServletPath().equals("/user");
    }
}
