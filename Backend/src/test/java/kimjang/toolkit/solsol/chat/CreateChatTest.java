package kimjang.toolkit.solsol.chat;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kimjang.toolkit.solsol.SolsolApplication;
import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.entity.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest(classes = SolsolApplication.class)
@ActiveProfiles(value = "dev")
@Transactional
public class CreateChatTest {
    @Autowired
    ChatService chatService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRepository chatMessageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Customer customer1;
    private Customer customer2;
    private ChatRoom chatRoom;
    private ChatRoomCustomerRelationship relationship1;
    private ChatRoomCustomerRelationship relationship2;



    @Test
    @DisplayName(value = "채팅 생성 테스트!")
    public void Send_Message_CreateChat(){
        SendChatMessageDto testMessage = SendChatMessageDto.builder().roomId(chatRoom.getId())
                .content("호식이 두마리 치킨 크크크 치킨은 회애!")
                .createDate(LocalDateTime.of(2023,12,12,20,0))
                .sender(new CustomerDto(customer2.getId(), "효승이"))
                .build();

        // 보낸 메세지 저장
        chatService.saveChat(testMessage);

        List<ChatMessage> chats = chatMessageRepository.findByChatRoom_Id(chatRoom.getId());

        Assertions.assertEquals(1, chats.size());
    }

    @BeforeEach
    public void setUp(){
        // Customer 1 생성
        customer1 = Customer.builder()
                .name("Customer 1")
                .email("customer1@example.com")
                .mobileNumber("01000000001")
                .createDt(LocalDateTime.now())
                .build();
        customerRepository.save(customer1);

        // Customer 2 생성
        customer2 = Customer.builder()
                .name("Customer 2")
                .email("customer2@example.com")
                .mobileNumber("01000000002")
                .createDt(LocalDateTime.now())
                .build();
        customerRepository.save(customer2);

        // Chat Room 생성
        chatRoom = ChatRoom.builder()
                .createDate(LocalDateTime.now())
                .build();
        chatRoomRepository.save(chatRoom);
    }

    @AfterEach
    public void cleanUp() {
        Long maxChatId = chatMessageRepository.findByChatRoom_Id(chatRoom.getId())
                .stream().mapToLong(ChatMessage::getId).max().orElseThrow();
        Long maxCustomerId = customer2.getId();
        Long maxChatRoomId = chatRoom.getId();
        log.info("마무리");
        // 생성한 Chat 제거
        chatMessageRepository.deleteAll();

        // AUTO_INCREMENT 값 1개 감소 (Chat Message)
        entityManager.createNativeQuery("ALTER TABLE chat_message AUTO_INCREMENT="+(maxChatId-1)).executeUpdate();
        // Customer 1 제거
        customerRepository.delete(customer1);

        // Customer 2 제거
        customerRepository.delete(customer2);

        // AUTO_INCREMENT 값 2개 감소 (Customer)
        entityManager.createNativeQuery("ALTER TABLE customer AUTO_INCREMENT="+(maxCustomerId-2)).executeUpdate();

        // Chat Room 제거
        chatRoomRepository.delete(chatRoom);

        // AUTO_INCREMENT 값 1개 감소 (Chat Room)
        entityManager.createNativeQuery("ALTER TABLE chat_room AUTO_INCREMENT="+(maxChatRoomId-1)).executeUpdate();
    }
}
