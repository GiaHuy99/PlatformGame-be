package exe201.flexora.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Cấu hình để Spring sử dụng Redis làm message broker
        config.enableStompBrokerRelay("/topic") // Các topic chat sẽ bắt đầu bằng /topic
                .setRelayHost("localhost")
                .setRelayPort(61613) // Cổng mặc định của STOMP plugin trong Redis
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemHeartbeatSendInterval(20000)   // Server sẽ gửi heartbeat sau mỗi 20 giây
                .setSystemHeartbeatReceiveInterval(20000);

        // Prefix cho các endpoint mà client sẽ gửi tin nhắn đến
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint để client kết nối WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Cho phép kết nối từ mọi nguồn
                .withSockJS();
    }
}
