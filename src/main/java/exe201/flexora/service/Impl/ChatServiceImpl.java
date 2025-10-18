package exe201.flexora.service.Impl;

import exe201.flexora.dto.ChatMessageDTO;
import exe201.flexora.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {
    private static final String CHAT_HISTORY_KEY_PREFIX = "chat:history:room:";
    // Thời gian cache sẽ tự động xóa nếu không có tin nhắn mới (ví dụ: 24 giờ)
    private static final long CACHE_EXPIRATION_HOURS = 24;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void saveMessage(ChatMessageDTO message) {
        String key = CHAT_HISTORY_KEY_PREFIX + message.getRoomId();
        long timestamp = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(key, message, timestamp);
        redisTemplate.expire(key, CACHE_EXPIRATION_HOURS, TimeUnit.HOURS);
    }
    @Override
    public Set<Object> getMessageHistory(Long roomId) {
        String key = CHAT_HISTORY_KEY_PREFIX + roomId;
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
