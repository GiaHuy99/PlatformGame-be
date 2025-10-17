package exe201.flexora.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class JoinRoomDTO {
    private Long userId;
    private String passWord;
}
