package kimjang.toolkit.solsol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 메세지 브로커가 WebSocket 메세지를 처리한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 메모리 기반 메세지 브로커를 동작시킴 그리고 "/topic" 접두사를 가진 구독 URI에게 메세지 전달을 수행함
		// 메세지 구독 요청 uri
		config.enableSimpleBroker("/topic");
		// 애플리케이션의 모든 "@MessageMapping의 URI" 앞에 접두사를 설정 즉, 현재 설정으로는 "/app/hello"가 메세지 보내는 endpoint가 된다.
		// 메세지 발행 요청 uri prefix
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// websocket 등록을 위한 endpoint 설정 현재는 "/gs-guide-websocket".
		registry.addEndpoint("/gs-guide-websocket")
				.setAllowedOriginPatterns("*"); // CORS 허용 범위;
	}

}