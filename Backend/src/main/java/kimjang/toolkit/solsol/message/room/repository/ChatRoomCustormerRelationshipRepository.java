package kimjang.toolkit.solsol.message.room.repository;

import kimjang.toolkit.solsol.message.room.ChatRoomCustomerRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomCustormerRelationshipRepository extends JpaRepository<ChatRoomCustomerRelationship, Long> {
    List<ChatRoomCustomerRelationship> findByChatRoom_Id(Long roomId);
}
