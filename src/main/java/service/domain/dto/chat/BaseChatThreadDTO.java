package service.domain.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.dto.foundation.BaseFoundationDTO;
import service.domain.dto.users.BaseUserDTO;
import service.domain.entity.chat.ChatThread;
import service.domain.types.chat.ChatStatus;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseChatThreadDTO extends BaseDTO {

    private BaseUserDTO user;
    private ChatStatus status;
    private String subject;
    private BaseFoundationDTO foundation;

    public BaseChatThreadDTO(ChatThread entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.user = new BaseUserDTO(entity.getUser());
            this.status = entity.getStatus();
            this.subject = entity.getSubject();
            this.foundation = new BaseFoundationDTO(entity.getFoundation());
        }
    }

}