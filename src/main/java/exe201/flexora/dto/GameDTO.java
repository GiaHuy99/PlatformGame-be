package exe201.flexora.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GameDTO {
    private Long id;
    private String name;
}
