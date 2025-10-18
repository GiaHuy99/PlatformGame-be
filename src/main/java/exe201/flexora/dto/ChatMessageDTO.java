package exe201.flexora.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageDTO {
    private String sender;
    private Long senderId; // Thêm ID người gửi
    private String content;
    private Long roomId;
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
