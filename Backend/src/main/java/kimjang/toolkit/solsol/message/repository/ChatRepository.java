package kimjang.toolkit.solsol.message.repository;

import kimjang.toolkit.solsol.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_Id(Long id);
}
