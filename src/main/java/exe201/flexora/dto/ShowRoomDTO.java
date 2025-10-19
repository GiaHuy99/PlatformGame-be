package exe201.flexora.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ShowRoomDTO {
    private String name;
    private String description;
    private String joinPolicy;
    private int  capacity;
    private int membersCount;
}
