package exe201.flexora.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class CommunityDTO {
    private Long id;
    private String name;
    private String description;
    private String school;
    private Boolean isPublic;
    private int membersCount;
    private int clubCount;
    private int eventCount;
    private List<GameDTO> gameDTO;  // FIX: Thêm để map từ Set<Game>}
 }
