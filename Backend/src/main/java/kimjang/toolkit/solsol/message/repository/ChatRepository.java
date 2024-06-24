package kimjang.toolkit.solsol.message.repository;

import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.GetChatProj;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.room.dto.LastChatDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_Id(Long id);

//    Slice<GetChatProj> findByChatRoom_Id(Long id, Pageable pageable);

    /**
     * 채팅 방 퇴장 시간 이후에 발생한 모든 채팅 불러오기
     *
     * @param roomId
     * @Param roomExitTime : 사용자가 최근 방에서 나간 시간
     * @param pageable : size(100)개 씩 채팅 전달
     * @return
     */
    @Query(value="SELECT " +
            " new kimjang.toolkit.solsol.message.dto.SendChatMessageDto(" +
            " cr.id, cm.content, cm.createDate, u.id, u.name )" +
            " FROM ChatMessage cm join ChatRoom cr ON cr.id = :roomId" +
            " join User u ON u.id = cm.user.id" +
            " WHERE cm.createDate >= :roomExitTime" + // 사용자가 채팅 방을 나간 시간
            " ORDER BY cm.createDate ASC") // 오래된 순서로 채팅 정렬
    Slice<SendChatMessageDto> findPastChats(@Param("roomId") Long roomId, @Param("roomExitTime") LocalDateTime roomExitTime, Pageable pageable);

    // 유저가 속한 채팅방에 가장 최근 채팅만 쿼리
    @Query(value = "Select new kimjang.toolkit.solsol.room.dto.LastChatDto(cm.createDate, cm.content) " +
            "FROM ChatMessage cm " +
            " WHERE cm.createDate = (select MAX(ccm.createDate) from ChatMessage ccm" +
            " Where ccm.chatRoom.id = cm.chatRoom.id)")
    // userId가 속한 채팅 방에서
    // 채팅 방의 채팅 중 가장 최근 채팅을 projection
    List<LastChatDto> findLastChatsByUserId(Long userId);
}
