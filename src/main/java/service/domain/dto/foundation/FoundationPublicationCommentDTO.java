package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.entity.foundation.FoundationPublicationComment;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoundationPublicationCommentDTO extends BaseFoundationPublicationCommentDTO {

    private List<BaseFoundationPublicationCommentDTO> replies;

    public FoundationPublicationCommentDTO(FoundationPublicationComment comment) {
        super(comment);
        if (Objects.nonNull(comment)) {
            this.replies = comment.getReplies().stream().map(BaseFoundationPublicationCommentDTO::new).toList();
        }
    }

}