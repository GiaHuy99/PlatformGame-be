package exe201.flexora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Bật CORS để client từ một nguồn khác (ví dụ: React app) có thể kết nối
                .cors(Customizer.withDefaults())

                // TẮT CSRF: Đây là bước BẮT BUỘC để SockJS/STOMP hoạt động
                .csrf(csrf -> csrf.disable())

                // Cấu hình các quy tắc cho phép truy cập (Authorization)
                .authorizeHttpRequests(auth -> auth
                        // Cho phép TẤT CẢ các kết nối đến endpoint WebSocket ("/ws") và các đường dẫn con của nó
                        .requestMatchers("/ws/**").permitAll()

                        // Tạm thời cho phép tất cả các request khác để dễ test
                        // 🚨 Cảnh báo: Trong môi trường production, bạn cần làm chặt chẽ hơn!
                        .anyRequest().permitAll()
                );

        return http.build();
    }

}
