package kimjang.toolkit.solsol.user.service;

import kimjang.toolkit.solsol.user.User;
import kimjang.toolkit.solsol.user.reposiotry.UserRepository;
import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registerUser(CreateUserDto createUserDto){
        String hashPwd = passwordEncoder.encode(createUserDto.getPwd());
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

    public UserProfileDto findUserProfileByEmail(String email){
        return userRepository.findProfileByEmail(email);
    }
}
