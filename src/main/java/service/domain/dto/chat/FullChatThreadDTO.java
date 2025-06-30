package service.domain.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.entity.chat.ChatThread;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullChatThreadDTO extends BaseChatThreadDTO {

    private List<ChatMessageDTO> message;

    public FullChatThreadDTO(ChatThread entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.message = entity.getMessage().stream().map(ChatMessageDTO::new).toList();
        }
    }

}