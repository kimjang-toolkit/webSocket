package kimjang.toolkit.solsol.user.service;

import kimjang.toolkit.solsol.user.entities.Friend;
import kimjang.toolkit.solsol.user.entities.User;
import kimjang.toolkit.solsol.user.reposiotry.FriendRepository;
import kimjang.toolkit.solsol.user.reposiotry.UserRepository;
import kimjang.toolkit.solsol.user.dto.AddFriendsDto;
import kimjang.toolkit.solsol.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public void addFriend(String email, AddFriendsDto addFriendsDto) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다,"));
        List<User> users = userRepository.findByIdIn(addFriendsDto.getFriendIds());
        if(addFriendsDto.getFriendIds().size() != users.size()){
            throw new RuntimeException("존재하지 않는 유저는 추가할 수 없습니다.");
        }
        List<Friend> friends = users.stream().map(subUser -> Friend.of(user, subUser)).collect(Collectors.toList());
        users.forEach(mainUser -> friends.add(Friend.of(mainUser, user)));
        friendRepository.saveAll(friends);
    }

    @Transactional
    public void removeFriend(String email, AddFriendsDto friendIds) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다,"));
        friendRepository.deleteByUser_EmailAndSubUser_IdIn(email, friendIds.getFriendIds());
    }

    public List<UserProfileDto> getFriends(String email){
        return friendRepository.findFriendsByEmail(email);
    }
}
