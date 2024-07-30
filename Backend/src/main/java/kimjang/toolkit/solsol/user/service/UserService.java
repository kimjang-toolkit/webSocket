package kimjang.toolkit.solsol.user.service;

import kimjang.toolkit.solsol.user.entities.User;
import kimjang.toolkit.solsol.user.dto.*;
import kimjang.toolkit.solsol.user.reposiotry.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtIssuer jwtIssuer;
    @Transactional
    public UserDto registerUser(CreateUserDto createUserDto){
        String hashPwd = passwordEncoder.encode(createUserDto.getPwd());
        Optional<User> userOptional = userRepository.findByEmail(createUserDto.getEmail());
        if(userOptional.isPresent()){
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = User.of(createUserDto, hashPwd);
        User savedUser = userRepository.save(user);
        return UserDto.toDto(savedUser);
    }

    public boolean checkUniqueEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return UserDto.toDto(user);
    }

    public LoginSuccessDto userLoginAndSaveRefreshToken(LoginDto loginDto){
        try{
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new AuthenticationException("존재하지 않는 아이디입니다."));
            if (!passwordEncoder.matches(loginDto.getPwd(), user.getPwd())) {
                log.error("bad credentials : {}",loginDto.getPwd());
                throw new BadCredentialsException("bad credential: using unmatched password");
            }
            log.info("email : "+loginDto.getEmail());
            String accessToken = jwtIssuer.getAccessToken(user.getEmail(), user.getAuthorities());
            String refreshToken = jwtIssuer.getRefreshToken(user.getEmail(), user.getAuthorities());
            return LoginSuccessDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .imgUrl(user.getImgUrl())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public LoginSuccessDto getAccessToken(RefreshDto refreshDto) {
        return jwtIssuer.getAccessTokenByRefreshToken(refreshDto);
    }

    public UserProfileDto getUserDetail() {
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        return userRepository.findProfileByEmail(userEmail);
    }
}
