package kimjang.toolkit.solsol.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // JwtAuthenticationFilter에서 request에 담아서 보내준 예외를 처리
        System.out.println(authException.getMessage()+" class : "+authException.getClass().getName());
        resolver.resolveException(request, response, null, authException);
//        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }
}