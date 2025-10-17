package exe201.flexora.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ClubMemberDTO {
    private Long userId;
    private String userName;
    private String userAvatarUrl;

    // Thông tin về club
    private Long clubId;
    private String clubName;

    // Thông tin về tư cách thành viên
    private String role;
    private LocalDateTime joinedAt;
}
