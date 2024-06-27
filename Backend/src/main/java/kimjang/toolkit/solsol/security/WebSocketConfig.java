
package kimjang.toolkit.solsol.security;

import kimjang.toolkit.solsol.security.handler.LoggingHandshakeInterceptor;
import kimjang.toolkit.solsol.security.handler.LoggingWebSocketHandlerDecoratorFactory;
import kimjang.toolkit.solsol.security.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // 메세지 브로커가 WebSocket 메세지를 처리한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final ChannelInterceptor stompChannelInterceptor;
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

	/**
	 * Security가 인식하지 못하는 헤더 값 속 JWT를 찾아서 검증한 후
	 * Authentication에 저장하기 like AuthenticationProvider
	 * @param registration
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration){
		registration.interceptors(stompChannelInterceptor);
	}

}