package kimjang.toolkit.solsol.room;

import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.entity.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.repository.ChatRoomCustormerRelationshipRepository;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import kimjang.toolkit.solsol.room.service.ChatRoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CreateRoomTest {
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    ChatRoomCustormerRelationshipRepository relationshipRepository;
    @Test
    @DisplayName("채팅방 생성 및 관계 생성 테스트")
    public void createRoomTest(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효")),
                "", "효승이 자니??", new CustomerDto(2L, "조승효"));
        Long roomId = chatRoomService.createChatRoomAndFirstChat(createChatRoomDto);
        ChatRoom savedRoom = chatRoomRepository.findById(roomId).orElseThrow();
        List<ChatRoomCustomerRelationship> savedRelationship = relationshipRepository.findByChatRoom_Id(savedRoom.getId());
        Assertions.assertEquals(createChatRoomDto.getParticipants().size(), savedRelationship.size());
    }

}
