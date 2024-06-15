package kimjang.toolkit.solsol.message;

import jakarta.persistence.*;
import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.message.room.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 엔티티는 불변해야하니까 final로 설정
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "message_id") // 컬럼의 이름을 바꿀 수 있다.
    private Long id;
    @JoinColumn(name = "chat_room")
    @ManyToOne
    private ChatRoom chatRoom; // 어디서 보냈는지
    private String content; // 뭘 보냈는지
    @Column(name = "create_date")
    private LocalDateTime createDate; // 언제 보냈는지
    @ManyToOne
    private Customer customer; // 누가 보냈는지
}
