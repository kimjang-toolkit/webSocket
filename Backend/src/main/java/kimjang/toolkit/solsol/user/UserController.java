package kimjang.toolkit.solsol.user;

import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.user.dto.UserProfileDto;
import kimjang.toolkit.solsol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userService.registerUser(createUserDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/register/unique")
    public ResponseEntity<Boolean> checkUniqueEmail(@RequestParam("email") String email){
        try{
            boolean check = userService.checkUniqueEmail(email);
            return ResponseEntity.ok(check);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user")
    public UserProfileDto getUserDetailsAfterLogin(Authentication authentication) {
        System.out.println(authentication.getName()+"님의 유저 정보 불러오기");
        return userService.findUserProfileByEmail(authentication.getName());
    }

    @GetMapping("/friends")
    public List<UserProfileDto> getUserFriends(Authentication authentication){
        return userService.findFriendsByEmail(authentication.getName());
    }
}
