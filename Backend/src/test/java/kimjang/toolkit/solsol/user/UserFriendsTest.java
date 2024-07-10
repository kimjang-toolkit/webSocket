package kimjang.toolkit.solsol.user;

import kimjang.toolkit.solsol.user.dto.UserProfileDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles(value = "dev")
@SpringBootTest
public class UserFriendsTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName(value = "유저 이메일이 유니크한지 체크")
    public void Check_Unique_Email(){
        List<UserProfileDto> friends = userRepository.findFriendsByEmail("abs");
        List<User> allUser = userRepository.findAll();
        Assertions.assertEquals(friends.size(), allUser.size());
    }
}
