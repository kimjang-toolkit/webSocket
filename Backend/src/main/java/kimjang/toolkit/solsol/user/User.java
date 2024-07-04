package kimjang.toolkit.solsol.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kimjang.toolkit.solsol.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "user_id") // 컬럼의 이름을 바꿀 수 있다.
    private Long id;

    private String name;

    private String email;

    private String pwd;

    @OneToMany(mappedBy="user",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @JoinColumn(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "create_dt")
    private LocalDateTime createDate;

    @Column(name = "img_url")
    private String imgUrl;


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
        user.authorities.add(Authority.of(user));
        return user;
    }
}