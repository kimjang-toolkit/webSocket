package kimjang.toolkit.solsol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker // 메세지 브로커가 WebSocket 메세지를 처리한다.
@EnableWebSocketSecurity // websocket 메세지에 대한 보안을 적용한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Bean
	AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
		messages.nullDestMatcher().authenticated()
				.simpDestMatchers("/pub/chat/**").hasRole("USER")
				.simpSubscribeDestMatchers("/notification/room/**", "/sub/chat/**").hasRole("USER")
				.anyMessage().denyAll();
		return messages.build();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 메모리 기반 메세지 브로커를 동작시킴 그리고 "/topic" 접두사를 가진 구독 URI에게 메세지 전달을 수행함
		// 메세지 구독 요청 uri
		config.enableSimpleBroker("/sub", "/notification", "/chat");

		// 애플리케이션의 모든 "@MessageMapping의 URI" 앞에 접두사를 설정 즉, 현재 설정으로는 "/app/hello"가 메세지 보내는 endpoint가 된다.
		// 메세지 발행 요청 uri prefix
		config.setApplicationDestinationPrefixes("/pub");
	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// websocket 등록을 위한 endpoint 설정 현재는 "/gs-guide-websocket".
		registry.addEndpoint("/gs-guide-websocket")
				.setAllowedOriginPatterns("*"); // CORS 허용 범위;
	}
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(loggingWebSocketHandlerDecoratorFactory()); // Add the decorator factory here
	}

	@Bean
	public HandshakeInterceptor loggingHandshakeInterceptor() {
		return new LoggingHandshakeInterceptor();
	}

	@Bean
	public WebSocketHandlerDecoratorFactory loggingWebSocketHandlerDecoratorFactory() {
		return new LoggingWebSocketHandlerDecoratorFactory();
	}
}