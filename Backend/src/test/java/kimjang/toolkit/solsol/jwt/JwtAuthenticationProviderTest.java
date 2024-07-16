package kimjang.toolkit.solsol.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kimjang.toolkit.solsol.config.jwt.JwtAuthenticateToken;
import kimjang.toolkit.solsol.config.jwt.JwtInvalidException;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.config.provider.JwtAuthenticationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtAuthenticationProviderTest {

    final Long ONE_SECONDS = 1000L;
    final Long ONE_MINUTE = 60 * ONE_SECONDS;
    final String KEY_ROLES = "authorities";
    final SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    JwtAuthenticationProvider provider;

    @BeforeEach
    public void setup() {
        provider = new JwtAuthenticationProvider();
    }

    private String createToken(String userName, String roles, Date now, int expireMin) {

        return Jwts.builder()
                .claim("email", userName)
                .claim("authorities", roles)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ONE_MINUTE * expireMin))
                .signWith(key)
                .compact();
    }

    @Test
    public void givenNotSupportAuthentication_whenCallSupports_thenReturnFalse() {

        assertThat(provider.supports(UsernamePasswordAuthenticationToken.class), equalTo(false));
        assertThat(provider.supports(AbstractAuthenticationToken.class), equalTo(false));
        assertThat(provider.supports(Authentication.class), equalTo(false));
    }

    @Test
    public void givenSupportAuthentication_whenCallSupports_thenReturnTrue() {

        assertThat(provider.supports(JwtAuthenticateToken.class), equalTo(true));
    }

    @Test
    public void givenExpiredToken_whenCallAuthentication_thenThrowJwtInvalidException() {

        Date past = new Date(System.currentTimeMillis() - ONE_MINUTE * 10);
        String invalidToken = createToken("solsol@naver.com", "ROLE_ADMIN,ROLE_USER,ROLE_MANAGER", past, 5);
        JwtAuthenticateToken authentication = new JwtAuthenticateToken(invalidToken);

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable, isA(JwtInvalidException.class));
        assertThat(throwable.getMessage(), equalTo("expired token"));
    }

    @Test
    public void givenMultiRole_whenCallAuthentication_thenReturnMultiGrantedAuthority() {

        Date expire = new Date(System.currentTimeMillis() + ONE_MINUTE * 10);
        String invalidToken = createToken("solsol@naver.com", "ROLE_ADMIN,ROLE_USER,ROLE_MANAGER", expire, 5);
        JwtAuthenticateToken authentication = new JwtAuthenticateToken(invalidToken);
        Authentication authenticated = provider.authenticate(authentication);
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authenticated.getAuthorities();
        System.out.println(authenticated);
        System.out.println("name : "+authenticated.getName());
        assertEquals(3, authorities.size());
    }

    @Test
    public void givenMalformedToken_whenCallAuthentication_thenThrowJwtInvalidException() {

        JwtAuthenticateToken authentication = new JwtAuthenticateToken("some malformed token here");

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable, isA(JwtInvalidException.class));
        assertThat(throwable.getMessage(), equalTo("malformed token"));
    }

    @Test
    public void givenNullJwt_whenCallAuthentication_thenThrowJwtInvalidException() {

        JwtAuthenticateToken authentication = new JwtAuthenticateToken((String) null);

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable, isA(JwtInvalidException.class));
        assertThat(throwable.getMessage(), equalTo("using illegal argument like null"));
    }

    @Test
    public void givenValidToken_whenCallAuthentication_thenReturnAuthentication() {

        String validToken = createToken("Junhyunny", "ROLE_ADMIN", new Date(), 30);
        JwtAuthenticateToken authentication = new JwtAuthenticateToken(validToken);

        Authentication authenticated = provider.authenticate(authentication);

        assertThat(authenticated.getPrincipal(), equalTo("Junhyunny"));
        assertThat(authenticated.getCredentials(), equalTo(""));
        Collection<? extends GrantedAuthority> authorities = authenticated.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            assertThat(authority.getAuthority(), equalTo("ROLE_ADMIN"));
        }
    }
}