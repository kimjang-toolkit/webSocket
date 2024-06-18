package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
