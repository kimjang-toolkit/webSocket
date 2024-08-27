package kimjang.toolkit.solsol.user.entities;

import jakarta.persistence.*;
import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // 컬럼의 이름을 바꿀 수 있다.
    private Long id;

    private String name;

    private String email;

    private String pwd;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @JoinColumn(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "create_dt")
    private LocalDateTime createDate;

    @Column(name = "img_url")
    private String imgUrl;
//
//    @OneToMany(mappedBy = "user",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Friend> friends = new HashSet<>();

    public static User of(CreateUserDto dto, String hashPwd){
        User user =  User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .pwd(hashPwd)
                .mobileNumber(dto.getMobileNumber())
                .createDate(LocalDateTime.now())
                .authorities(new HashSet<>())
                .imgUrl(dto.getImgUrl())
                .build();
        return user;
    }

    public void addAuthorities(List<Authority> authorityList){
        authorities.addAll(authorityList);
    }

//    public void addFriend(List<Friend> friend) {
//        friends.addAll(friend);
//    }
//
//    public void removeFriend(Long subUserId) {
//        for(Friend friend : friends){
//            if(friend.getSubUser().getId().equals(subUserId)){
//                friends.remove(friend);
//            }
//        }
//        throw new RuntimeException("친구가 아닌 사람입니다.");
//    }
}