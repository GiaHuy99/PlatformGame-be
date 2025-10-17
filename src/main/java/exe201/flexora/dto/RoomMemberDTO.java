package exe201.flexora.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomMemberDTO {
    // Thông tin về người dùng (chỉ lấy những gì cần thiết)
    private Long userId;
    private String userName;
    private String userAvatarUrl;

    // Thông tin về phòng (chỉ lấy những gì cần thiết)
    private Long roomId;
    private String roomName;

    // Thông tin về tư cách thành viên
//    private String role;
    private LocalDateTime joinedAt;
}
