package kimjang.toolkit.solsol.user;

import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userService.registerUser(createUserDto);
        return ResponseEntity.ok(userDto);
    }
    @RequestMapping("/user")
    public UserDto getUserDetailsAfterLogin(Authentication authentication) {
        System.out.println(authentication.getName()+"님의 유저 정보 불러오기");
        return userService.findByEmail(authentication.getName());
    }
}
