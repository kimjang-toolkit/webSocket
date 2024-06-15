package kimjang.toolkit.solsol.message.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kimjang.toolkit.solsol.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "room_id") // 컬럼의 이름을 바꿀 수 있다.
    private Long id; // 방 번호
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 언제 만들어졌는지

}
