package kimjang.toolkit.solsol.message.repository;

import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {


//    Slice<GetChatProj> findByChatRoom_Id(Long id, Pageable pageable);

    /**
     * 요청한 유저의 채팅 방 퇴장 시간
     * 이후에 발생한 모든 채팅 불러오기
     *
     * @param roomId
     * @param userId
     * @param pageable : size(100)개 씩 채팅 전달
     * @return
     * @Param roomExitTime : 사용자가 최근 방에서 나간 시간
     */
    @Query(value="SELECT " +
            " new kimjang.toolkit.solsol.message.dto.SendChatMessageDto(" +
            " :roomId, cm.content, cm.createDate, u.id, u.email, u.name )" + // 채팅 내용
            " FROM ChatMessage cm " + // 채팅
            " join User u ON u.id = cm.user.id " + // 유저
            " WHERE cm.createDate > (" +
            " SELECT MAX(ccr.roomExitTime) FROM ChatRoomCustomerRelationship ccr " +
            " WHERE ccr.user.id = :userId AND cm.chatRoom.id = :roomId )" + // 사용자가 채팅 방을 나간 시간 이후에 생성된 채팅들
            " ORDER BY cm.createDate ASC ") // 오래된 순서로 채팅 정렬
    Slice<SendChatMessageDto> findRecentChats(@Param("roomId") Long roomId, @Param("userId") Long userId, Pageable pageable);

    // 유저가 속한 채팅방에 가장 최근 채팅만 쿼리
    @Query(value="SELECT " +
            " new kimjang.toolkit.solsol.message.dto.SendChatMessageDto(" +
            " :roomId, cm.content, cm.createDate, u.id, u.email, u.name )" + // 채팅 내용
            " FROM ChatMessage cm " + // 채팅
            " join User u ON u.id = cm.user.id " + // 유저
            " WHERE cm.createDate <= (" +
            " SELECT MAX(ccr.roomExitTime) FROM ChatRoomCustomerRelationship ccr " +
            " WHERE ccr.user.id = :userId AND cm.chatRoom.id = :roomId )" + // 사용자가 채팅 방을 나간 시간 이전에 생성된 채팅들
            " ORDER BY cm.createDate ASC ") // 최신 순서로 채팅 정렬
    Slice<SendChatMessageDto> findPastChats(@Param("roomId") Long roomId, @Param("userId") Long userId, Pageable pageable);

}
