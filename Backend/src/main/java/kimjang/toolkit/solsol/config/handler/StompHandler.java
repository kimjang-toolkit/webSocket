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

        log.info("목적지 : " + destination+" 세션 id : "+sessionId);
        log.info("토큰 " + token);
        // STOMP 메서드가 CONNECT인 경우
        if (StompCommand.CONNECT.equals(accessor.getCommand()) && token != null) {
            log.info("토큰 유효성 검사 시작!");
//            jwtTokenValidator.sub(token);
            String email = jwtTokenValidator.getEmail(sessionId);
            sessionContainer.setEmail(sessionId, email);
        }
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) && destination != null) {
            try {
                Matcher matcher = CHAT_ROOM_PATTERN.matcher(destination);
                if (matcher.matches()) {
                    Long roomId = Long.valueOf(matcher.group(1));
                    log.info("subscribe roomId : " + roomId);
//                    jwtAuthenticationProvider.isValid(token);
                    sessionContainer.subscribe(sessionId, roomId); // 세션에 구독 채팅방 키 저장
                } else {
                    log.info("Invalid destination format: " + destination);
                }
            } catch (IllegalStateException | BadCredentialsException e) {
                log.error(e.getMessage(), e);
                throw e;
            }
        }

        // 채팅방 구독 해제하는 경우
        if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
//            System.out.println("message:" + message);
//            System.out.println("헤더 : " + message.getHeaders());
//            System.out.println("요청 메서드 : " + accessor.getCommand());
            try{
//                String email = jwtAuthenticationProvider.isValid(token);
                Long roomId = sessionContainer.unsubscribe(sessionId);
                String email = sessionContainer.unsetEmail(sessionId);
                log.info("방 번호 : {}, 이메일 : {}", roomId, email);
                subscribeService.updateUnsubscribeTime(email, roomId);

            } catch (IllegalStateException e){
                System.out.println(e.getMessage());
                throw e;
            }

        }
        return message;
    }

}