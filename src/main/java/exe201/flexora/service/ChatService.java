package exe201.flexora.service;

import exe201.flexora.dto.ChatMessageDTO;

import java.util.Set;

public interface ChatService {
    public void saveMessage(ChatMessageDTO message);
    Set<Object> getMessageHistory(Long roomId);
}
