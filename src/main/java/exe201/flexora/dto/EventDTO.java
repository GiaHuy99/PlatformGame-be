package exe201.flexora.dto;

import exe201.flexora.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventDTO {
    private User organizer;
    private String title;
    private String description;
    private String mode;
    private String location;
    private Instant startsAt;
    private Instant endsAt;
    private Long priceCents;
    private Integer capacity;
    private Long escrowMinCents;
    private BigDecimal platformFeeRate;
    private String gatewayFeePolicy;
    private String status;

}
