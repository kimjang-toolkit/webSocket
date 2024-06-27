package kimjang.toolkit.solsol.security;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    // This method creates a bean of type AuthorizationManager<Message<?>>.
    // The AuthorizationManager is responsible for handling message-level authorization.
    // It takes a MessageMatcherDelegatingAuthorizationManager.Builder as a parameter.
//    @Bean
//    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        // The following code configures message-level authorization rules.
//        messages // Set public access for messages with destinations starting with "/pub/**" and "/sub/**".
//                .simpDestMatchers("/pub/**", "/gs-guide-websocket").permitAll()
//                .simpDestMatchers("/sub/**").permitAll()
//                .anyMessage().permitAll();
//        // Return the built MessageMatcherDelegatingAuthorizationManager.
//        return messages.build();
//    }
}