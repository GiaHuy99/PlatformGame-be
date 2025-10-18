package exe201.flexora.controller;

import exe201.flexora.dto.ChatMessageDTO;
import exe201.flexora.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessage, @DestinationVariable Long roomId) {
        chatMessage.setRoomId(roomId);

        if (ChatMessageDTO.MessageType.CHAT.equals(chatMessage.getType())) {
            chatService.saveMessage(chatMessage);
        }

        messagingTemplate.convertAndSend("/topic/room/" + roomId, chatMessage);
    }
}
