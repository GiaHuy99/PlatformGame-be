package exe201.flexora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Dòng này sẽ tự động sử dụng Bean "corsConfigurationSource" ở dưới
                .cors(Customizer.withDefaults())

                // TẮT CSRF: Bắt buộc cho SockJS/STOMP
                .csrf(csrf -> csrf.disable())

                // Cấu hình các quy tắc cho phép truy cập
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll() // Cho phép endpoint WebSocket
                        .anyRequest().permitAll()             // Tạm thời cho phép mọi thứ khác
                );

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép truy cập từ bất kỳ nguồn nào
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        // Cho phép tất cả các phương thức HTTP (GET, POST, PUT, DELETE, OPTIONS, v.v.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Cho phép tất cả các header
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Cho phép gửi cookie và thông tin xác thực
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Áp dụng cấu hình CORS này cho TẤT CẢ các đường dẫn trong ứng dụng
        source.registerCorsConfiguration("/**", configuration);

        return source;}

}
