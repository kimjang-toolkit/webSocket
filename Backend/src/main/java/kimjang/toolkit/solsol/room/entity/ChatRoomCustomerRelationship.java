package kimjang.toolkit.solsol.room.entity;

import jakarta.persistence.*;
import kimjang.toolkit.solsol.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCustomerRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id; // 고객 - 방 연관관계 번호

    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;
}
