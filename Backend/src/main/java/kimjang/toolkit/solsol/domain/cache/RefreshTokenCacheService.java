package kimjang.toolkit.solsol.domain.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenCacheService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Cacheable(value = "refresh")
    public String findRefreshToken(String email){
        return
    }

}
