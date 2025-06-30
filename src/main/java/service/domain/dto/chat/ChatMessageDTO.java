package service.domain.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.dto.users.BaseUserDTO;
import service.domain.entity.chat.ChatMessage;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDTO extends BaseDTO {

    private String message;
    private String attachmentUrl;
    private BaseUserDTO senderUser;

    public ChatMessageDTO(ChatMessage entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.message = entity.getMessage();
            this.attachmentUrl = entity.getAttachmentUrl();
            this.senderUser = new BaseUserDTO(entity.getSenderUser());
        }
    }

}