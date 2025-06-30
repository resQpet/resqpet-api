package service.domain.request.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatThreadRequest {

    @NotNull(message = "Foundation id is required")
    private Long foundationId;

    @NotNull(message = "Subject is required")
    private String subject;

}
