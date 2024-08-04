package kimjang.toolkit.solsol.config.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// not used
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    private final Long ONE_SECONDS = 1000L;
    private final Long ONE_MINUTE = 60 * ONE_SECONDS;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("jwt 토큰 생성 시도! email : "+authentication.getName());
        if (null != authentication) {
            // Key 자동 생성
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            // 서명자나 주제 같은 건 다 환경변수로 빼야한다.
            String jwt = Jwts.builder().issuer("Sol-Kimjang").subject("JWT Token")
                    // 비밀번호는 절대 안된다.
                    .claim("email", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    // 만료시간, ms 단위, 만료 시간을 넘어선 토큰이 들어오면 만료됐다고 예외가 발생한다.
                    .expiration(new Date((new Date()).getTime() + ONE_MINUTE*60))
                    // 디지털 서명 작성
                    .signWith(key).compact();

            response.setHeader(SecurityConstants.JWT_HEADER, SecurityConstants.BEARER_PREFIX+jwt);
            System.out.println(authentication.getName()+"님 jwt 발급됐습니다.");
        }

        filterChain.doFilter(request, response);

    }

    /**
     * 메서드의 조건을 통과하면 필터를 실행하지 않도록한다.
     * JWT 토큰 생성은 로그인 과정에서만 진행되어야한다.
     *  따라서 다른 path에 대한 요청에서는 실행하지 않아야한다.
     *  즉, "/login"가 아니라면 실행하지 않는 것
     *
     *  프론트는 아래처럼 Authorization과 XSRF-TOKEN을 세션 스토리지에 저장하고 있어야한다.
     *
     *  validateUser(loginForm: NgForm) {
     *     this.loginService.validateLoginDetails(this.model).subscribe(
     *       responseData => {
     *         window.sessionStorage.setItem("Authorization",responseData.headers.get('Authorization')!);
     *         this.model = <any> responseData.body;
     *         this.model.authStatus = 'AUTH';
     *         window.sessionStorage.setItem("userdetails",JSON.stringify(this.model));
     *         let xsrf = getCookie('XSRF-TOKEN')!;
     *         window.sessionStorage.setItem("XSRF-TOKEN",xsrf);
     *         this.router.navigate(['dashboard']);
     *       });
     *
     *   }
     *
     * @param request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println("auth request url : "+request.getServletPath());
        return !request.getServletPath().equals("/user") ;
    }

    // 권한 배열을 문자열로 변환하는 메서드
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
