package kimjang.toolkit.solsol.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @JoinColumn(name = "mobile_number")
    private String mobileNumber;

    // json -> 객체로 시리얼라이제이션은 가능하지만 그 반대로는 불가능하게 해서 외부로 DB의 비밀번호가 유출되지 않도록 함.
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String pwd;
//
//    private String role;
//
    @Column(name = "create_dt")
    private LocalDateTime createDt;

//    @JsonIgnore // 디-시리얼라이제이션 될 때 json으로 값을 넣지 말도록 함
//    @OneToMany(mappedBy = "user")
//    private Set<Authority> authorities;

}