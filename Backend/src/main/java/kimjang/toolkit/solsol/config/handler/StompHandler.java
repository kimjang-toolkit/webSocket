package kimjang.toolkit.solsol.config.handler;


import kimjang.toolkit.solsol.config.jwt.JWTTokenValidator;
import kimjang.toolkit.solsol.room.service.ChatRoomSubscribeService;
import kimjang.toolkit.solsol.config.container.SocketSessionContainer;
import kimjang.toolkit.solsol.config.jwt.SecurityConstants;
import kimjang.toolkit.solsol.config.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JWTTokenValidator jwtTokenValidator;
    private final ChatRoomSubscribeService subscribeService;
    private final SocketSessionContainer sessionContainer;
    // 정규식 패턴을 컴파일하여 재사용
    private static final Pattern CHAT_ROOM_PATTERN = Pattern.compile("^\\s*/sub/chat/(\\d+)\\s*$");
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String token = accessor.getFirstNativeHeader(SecurityConstants.JWT_HEADER);
        String destination = accessor.getDestination();

        String sessionId = accessor.getSessionId();
        log.info("Command : {}",accessor.getCommand());
        log.info("목적지 : " + destination+" 세션 id : "+sessionId);
        log.info("토큰 " + token);
        // STOMP 메서드가 CONNECT인 경우
        if (StompCommand.CONNECT.equals(accessor.getCommand()) && token != null) {
            log.info("토큰 유효성 검사 시작!");
            String email = jwtTokenValidator.getEmail(token);
            log.info("connect email : {}",email);
            sessionContainer.setEmail(sessionId, email);
        }
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) && destination != null) {
            String[] destList = destination.split("/");
            log.info("");
            try {
                if (destList.length >= 3 && destList[1].equals("sub") && destList[2].equals("chat")) {
                    Long roomId = Long.valueOf(destList[3]);
                    log.info("subscribe roomId : " + roomId);
                    sessionContainer.subscribe(sessionId, roomId); // 세션에 구독 채팅방 키 저장
                } else {
                    log.info("not subscribe chat destination : " + destination);
                }
            } catch (IllegalStateException | BadCredentialsException e) {
                log.error(e.getMessage(), e);
                throw e;
            }
        }

        // 채팅방 구독 해제하는 경우
        if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            try{
                Long roomId = sessionContainer.unsubscribe(sessionId);
                String email = sessionContainer.getEmail(sessionId);
                log.info("방 번호 : {}, 이메일 : {}", roomId, email);
                subscribeService.updateUnsubscribeTime(email, roomId);

            } catch (IllegalStateException e){
                log.error(e.getMessage(), e);
                throw e;
            }

        }
        return message;
    }

}