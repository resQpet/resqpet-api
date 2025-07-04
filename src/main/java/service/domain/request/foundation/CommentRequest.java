package service.domain.request.foundation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull(message = "The publication id is required")
    private Long publicationId;

    @NotNull(message = "The content is required")
    private String content;

    private Long parentCommentId;

}
