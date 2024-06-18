package kimjang.toolkit.solsol.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.room.dto.CreateRoomReqDto;
import kimjang.toolkit.solsol.message.room.service.ChatRoomService;
import kimjang.toolkit.solsol.message.room.service.ChatRoomStompService;
import kimjang.toolkit.solsol.message.room.service.CreateRoomName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InviteIntegrationTests {

	@LocalServerPort
	private int port;

	private WebSocketStompClient stompClient;
	@Autowired
	ChatRoomStompService chatRoomStompService;

	private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	@BeforeEach
	public void setup() {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
		this.stompClient = new WebSocketStompClient(webSocketClient);

		// LocalDateTime을 필드로 가지는 Dto 같은 경우 테스트 시 아래처럼 객체 맵퍼를 설정해줘야한다.
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);

		this.stompClient.setMessageConverter(converter);
	}

	@Test
	public void inviteCustomers() throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
				Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효"), new CustomerDto(3L, "강아지"), new CustomerDto(4L, "까미나무 삼계탕")),
				"", "효승이 자니??", new CustomerDto(2L, "조승효"));

		StompSessionHandler handler = new TestSessionHandler(failure) {

			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				for(int i=1;i<=3; i++){
					int finalI = i;
					CreateRoomReqDto createRoomReqDto = CreateRoomReqDto.builder()
							.roomId(1L)
							.roomName(CreateRoomName.withParticipationsName(createChatRoomDto, createChatRoomDto.getParticipants().get(i-1).getId()))
							.firstChat(createChatRoomDto.getFirstChat())
							.customer(createChatRoomDto.getMaker()).build();
					session.subscribe("/notification/room/"+i, new StompFrameHandler() {
						@Override
						public Type getPayloadType(StompHeaders headers) {
							return CreateRoomReqDto.class; // 응답 객체의 클래스
						}

						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							CreateRoomReqDto messageDto = (CreateRoomReqDto) payload;
							try {
								System.out.println(finalI +"로 보낸 요청 응답 : "+messageDto.getRoomName()+"으로 초대");
								assertEquals(createRoomReqDto.getRoomName(), messageDto.getRoomName());
							} catch (Throwable t) {
								failure.set(t);
							} finally {
								session.disconnect();
								latch.countDown();
							}
						}
					});
				}
				try {
					System.out.println("SEND : restapi");
//					session.send("/pub/chat/1", testMessage);
					chatRoomStompService.inviteParticipates(createChatRoomDto, 1L);
				} catch (Throwable t) {
					failure.set(t);
					latch.countDown();
				}
			}
		};

		this.stompClient.connectAsync("ws://localhost:{port}/gs-guide-websocket", this.headers, handler, this.port);

		if (latch.await(3, TimeUnit.SECONDS)) {
			if (failure.get() != null) {
				throw new AssertionError("", failure.get());
			}
		}
		else {
			fail("Greeting not received");
		}

	}

	private static class TestSessionHandler extends StompSessionHandlerAdapter {

		private final AtomicReference<Throwable> failure;

		public TestSessionHandler(AtomicReference<Throwable> failure) {
			this.failure = failure;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			this.failure.set(new Exception(headers.toString()));
		}

		@Override
		public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable ex) {
			this.failure.set(ex);
		}
	}
}