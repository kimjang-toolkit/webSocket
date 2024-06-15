package kimjang.toolkit.solsol.message.room.repository;

import kimjang.toolkit.solsol.message.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
