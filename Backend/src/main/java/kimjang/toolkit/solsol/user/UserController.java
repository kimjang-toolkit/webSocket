package kimjang.toolkit.solsol.user;

import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userService.registerUser(createUserDto);
        return ResponseEntity.ok(userDto);
    }
}
