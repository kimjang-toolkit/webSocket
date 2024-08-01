package kimjang.toolkit.solsol.domain.cache;

import kimjang.toolkit.solsol.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenCacheService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * CachePut은 캐시가 있더라도 메서드를 실행한 결과를 Cache에 저장
     * 따라서 무조건 새로운 token을 저장하고 싶은 경우 적절
     * 한 메일에 한 refresh token만 가능!
     *
     * @param email
     * @return
     */
    @CachePut(value= "refresh", key="#email")
    public RefreshToken saveToken(String email, String refreshToken){
        Optional<RefreshToken> token = refreshTokenRepository.findByEmail(email);
        if(token.isPresent()){ // 예전에 로그인해서 refresh token이 남아 있는 경우
            token.get().setRefreshToken(refreshToken);
            return refreshTokenRepository.save(token.get());
        } else{ // 처음 refresh token 만들어서 저장하는 경우
            RefreshToken newToken = RefreshToken.builder()
                    .email(email)
                    .refreshToken(refreshToken)
                    .build();
            return refreshTokenRepository.save(newToken);
        }
    }
}
