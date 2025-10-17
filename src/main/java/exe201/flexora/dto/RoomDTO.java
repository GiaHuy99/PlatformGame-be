package exe201.flexora.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class RoomDTO {
    private String name;
    private String description;
    private String joinPolicy;
    private String joinPasswordHash;
    private int  capacity;
    private int membersCount;
}
