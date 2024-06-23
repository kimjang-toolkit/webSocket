package kimjang.toolkit.solsol.config;

import kimjang.toolkit.solsol.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    /**
     * 커스텀 security filter chain
     *
     * @param http
     * @return
     * @throws Exception
     */


//    @Bean
//    public JdbcUserDetailsManager userDetailService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 토큰을 이용해서 원하는 브라우저에서만 요청할 수 있도록, CSRF 공격 방지
        // CSRF 토큰 값을 사용하는 헤더는 왜 CORS 설정을 하지 않았냐면, 프레임워크에서 csrf 헤더를 조작하기 때문이다.
//        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        // 모든 uri에 인증이 필요하지 않도록 설정
        // 한번 자격증명을 보내면 JSSESSIONID 값을 이용해서 보안 API 호출할 때 자격증명을 제공하지 않아도 괜찮은 상태
        // 세션 정보를 서버에 저장하지 않고 세션 ID를 제공하지 않도록함
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> {
                    cors.configurationSource(request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173", "http://localhost:5174","http://localhost:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        // 비밀번호 같은 자격증명을 헤더에 담아서 보낼 수 있도록 설정
//                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        // Authorization 헤더를 노출하는 것을 허용, 여기에 JWT 토큰 값을 보낼 것
//                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    });
                })
//                // CookieCsrfTokenRepository 는 csrf 토큰을 쿠키로 유지하고 헤더 "XSRF-TOKEN"이란 이름으로 csrf 토큰을 저장한다.
//                // withHttpOnlyFalse은 App UI의 javascript가 쿠키를 읽을 수 있도록 하는 설정 // postman을 동작시키기 위함
//                // csrf가 세션 스토리지에 저장하게됨
//                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/contact", "/register")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        // uri를 접근하기 위해 유저에게 권한이 있는지 체크 = 인가
                        // 역할에 ROLE_ 접두사를 붙일 필요 없음. security가 자동으로 붙여서 검색함.
                        .requestMatchers("/**").permitAll()
                        .requestMatchers( "/api-docs" ).permitAll())
//                // Resource server로 동작하기 위해 jwt 컨버터를 세팅
//                .oauth2ResourceServer(server ->
//                        server.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(converter)));
        // 이제 Spring 앱이 resource server 역할을 하기 때문에 로그인 기능을 제외한다.
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }



    /**
     * DaoAuthenticationManager가 InMamoryUserManager 내에 있는 메서드로 사용자를 인증하려고
     *                                                      loadUserByUsername 메서드 반환 유형은 UserDetails입니다.
     *                                                      이는 유저의 이름으로 저장소에 있던 세부사항을 가져와서 User 객체를 만드니다.
     *
     * 유저 정보를 메모리 상이 아닌 JDBC로 DB에 저장하기 때문에 InMemoryUserDetailsManager는 주석 처리
     * @return
     */
    @Bean
    public InMemoryUserDetailsManager userDetailService(){
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("user")
                .password("1234")
                .authorities("admin")
                .build();

        return new InMemoryUserDetailsManager(admin); // 메모리 상에 유저의 자격증명 정보를 저장
    }

    /**
     * spring security 프레임워크 전체에서 사용할 PasswordEncoder를 정의한다.
     *
     * 인증을 keycloak에 전담했으니 비밀번호를 다루지 않을 것이다.
     * @return
     */
//    @Bean
    public PasswordEncoder passwordEncoder(){
        // 비밀번호를 일반 텍스트로 관리되기 때문에 프로덕션 환경에서는 위험하다.
        // BCrypt 알고리즘을 이용해서 비밀번호를 해싱하자
        return new BCryptPasswordEncoder();
    }

}
