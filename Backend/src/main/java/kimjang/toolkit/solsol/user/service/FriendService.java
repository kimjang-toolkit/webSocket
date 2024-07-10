package kimjang.toolkit.solsol.user.service;

import kimjang.toolkit.solsol.user.User;
import kimjang.toolkit.solsol.user.UserRepository;
import kimjang.toolkit.solsol.user.dto.AddFriendsDto;
import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(String email, AddFriendsDto addFriendsDto) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다,"));
        List<User> friend = userRepository.findByIdIn(addFriendsDto.getFriendIds());
        if(addFriendsDto.getFriendIds().size() != friend.size()){
            throw new RuntimeException("존재하지 않는 유저는 추가할 수 없습니다.");
        }
        user.addFriend(friend);
    }

    @Transactional
    public void removeFriend(String email, Long friendId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다,"));
        user.removeFriend(friendId);
    }

    public List<UserProfileDto> getFriends(String email){
        return userRepository.findFriendsByEmail(email);
    }
}
