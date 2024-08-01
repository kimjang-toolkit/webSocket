package kimjang.toolkit.solsol.jwt;

import kimjang.toolkit.solsol.SolsolApplication;
import kimjang.toolkit.solsol.domain.cache.RefreshToken;
import kimjang.toolkit.solsol.domain.cache.RefreshTokenCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest(classes = SolsolApplication.class)
@ActiveProfiles(value = "dev")
@Transactional
public class RefreshTokenCacheServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenCacheService refreshTokenCacheService;

    @Test
    public void checkCacheableToken(){
        String email = "solsol@gmail.com";
        String refreshToken = "1234";
        String hashedRefreshToken = passwordEncoder.encode(refreshToken);

        // email에 맞는 refresh token 캐시에 저장
        refreshTokenCacheService.saveToken(email, hashedRefreshToken);
        // 캐시에 있는 refresh token 얻어오기
        String savedRefreshToken = refreshTokenCacheService.searchToken(email);
        Assertions.assertTrue(passwordEncoder.matches(refreshToken, savedRefreshToken));
    }
}
