package kimjang.toolkit.solsol.security.handler;

import kimjang.toolkit.solsol.security.jwt.SecurityConstants;
import kimjang.toolkit.solsol.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        System.out.println("message:" + message);
//        System.out.println("헤더 : " + message.getHeaders());
//        System.out.println("요청 메서드 : " + accessor.getCommand());
        String token = accessor.getFirstNativeHeader(SecurityConstants.JWT_HEADER);
//        System.out.println("토큰 " + token);
        // STOMP 메서드가 CONNECT인 경우
        if (StompCommand.CONNECT.equals(accessor.getCommand()) && token != null) {
            System.out.println("토큰 유효성 검사 시작!");
            jwtAuthenticationProvider.isValid(token);
        } else if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            System.out.println("구독 해제 합니다. 그런데 토큰은 오는지 보겠오");
            System.out.println("토큰 유효성 검사 시작!");
            jwtAuthenticationProvider.isValid(token);
        }
        return message;
    }

}