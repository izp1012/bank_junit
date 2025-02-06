package shop.mtcoding.bank.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import shop.mtcoding.bank.handler.websocket.WebSocketHandler;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {
private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebSocketHandler webSocketHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // 클라이언트가 구독할 주소 (예: "/topic/public")
        registry.setApplicationDestinationPrefixes("/app");  // 클라이언트가 메시지를 보낼 때의 prefix 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")  // WebSocket 연결 엔드포인트 설정
                .setAllowedOriginPatterns("*")
                .withSockJS();  // SockJS 지원 (브라우저 호환성)

        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/api/chat").setAllowedOrigins("*");
    }
}