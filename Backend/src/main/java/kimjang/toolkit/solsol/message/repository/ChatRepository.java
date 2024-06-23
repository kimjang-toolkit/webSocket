package kimjang.toolkit.solsol.message.repository;

import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.GetChatProj;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_Id(Long id);

    Slice<GetChatProj> findByChatRoom_Id(Long id, Pageable pageable);


//    @Query(value="SELECT ")
    List<SendChatMessageDto> findPastChats(Long roomId, Pageable pageable);
}
