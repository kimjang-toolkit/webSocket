package kimjang.toolkit.solsol.security.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;


public class StompHandler implements ChannelInterceptor {
//    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
        String auth = accessor.getFirstNativeHeader("Authorization");
        // STOMP 메서드가 CONNECT인 경우
        if (StompCommand.CONNECT.equals(accessor.getCommand()) && auth != null) {
            //connect라면 name값을 꺼내서 sessionAttributes에 넣기.
            Map<String, Object> attributes = accessor.getSessionAttributes();
            attributes.put("Authorization", auth);
            accessor.setSessionAttributes(attributes);
        }
        return message;
    }

}