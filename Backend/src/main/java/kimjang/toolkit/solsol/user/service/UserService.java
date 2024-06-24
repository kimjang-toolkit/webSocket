package kimjang.toolkit.solsol.user.service;

import kimjang.toolkit.solsol.user.User;
import kimjang.toolkit.solsol.user.UserRepository;
import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(CreateUserDto createUserDto){
        String hashPwd = passwordEncoder.encode(createUserDto.getPwd());
        User user = User.of(createUserDto, hashPwd);
        User savedUser = userRepository.save(user);
        return UserDto.toDto(savedUser);
    }
}
