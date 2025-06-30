package service.domain.request.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatMessageRequest {

    @NotNull(message = "Chat thread id is required")
    private Long chatThreadId;

    private String message;

}
