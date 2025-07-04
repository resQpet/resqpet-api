package service.services.foundation.publication.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.domain.entity.foundation.FoundationPublicationComment;
import service.domain.entity.user.User;
import service.domain.request.foundation.CommentRequest;
import service.services.BaseService;

public interface FoundationPublicationCommentService extends BaseService<FoundationPublicationComment> {

    FoundationPublicationComment create(CommentRequest request, User user);

    Page<FoundationPublicationComment> findAllByPublication(Long publicationId, Pageable pageable);

}
