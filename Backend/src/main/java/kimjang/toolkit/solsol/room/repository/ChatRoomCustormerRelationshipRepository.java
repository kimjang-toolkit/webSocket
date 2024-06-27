package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.entity.ChatRoomCustomerRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomCustormerRelationshipRepository extends JpaRepository<ChatRoomCustomerRelationship, Long> {
    List<ChatRoomCustomerRelationship> findByChatRoom_Id(Long roomId);
    void deleteByChatRoom_IdAndUser_Email(Long roomId, String email);
}
