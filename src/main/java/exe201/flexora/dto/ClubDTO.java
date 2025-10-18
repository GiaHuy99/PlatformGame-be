package exe201.flexora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ClubDTO {
    private String name;
    private String description;
    @JsonProperty("isPublic")  // FIX: Explicit map JSON key "isPublic"
    private boolean isPublic;
    private int membersCount;

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
