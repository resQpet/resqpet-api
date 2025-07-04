package service.services.foundation.publication.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.foundation.FoundationPublication;
import service.domain.entity.foundation.FoundationPublicationComment;
import service.domain.entity.user.User;
import service.domain.repository.foundation.FoundationPublicationCommentRepository;
import service.domain.request.foundation.CommentRequest;
import service.services.DefaultBaseService;
import service.services.foundation.publication.FoundationPublicationService;

import java.util.Objects;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationPublicationCommentService extends DefaultBaseService<FoundationPublicationComment> implements FoundationPublicationCommentService {

    private final FoundationPublicationCommentRepository repository;
    private final FoundationPublicationService publicationService;

    /**
     * Creates a new FoundationPublicationComment entity based on the provided request and user.
     *
     * @param request the CommentRequest object containing the details of the comment to be created.
     *                It includes the publication ID, the content of the comment,
     *                and optionally the parent comment ID if it's a reply.
     * @param user    the User object representing the author of the comment.
     * @return the created FoundationPublicationComment entity.
     */
    @Override
    @Transactional
    public FoundationPublicationComment create(CommentRequest request, User user) {
        FoundationPublication publication = this.publicationService.findById(request.getPublicationId());
        FoundationPublicationComment parentComment = null;

        if (Objects.nonNull(request.getParentCommentId())) {
            parentComment = this.findById(request.getParentCommentId());
            publication = null;
        }

        final FoundationPublicationComment comment = FoundationPublicationComment.builder()
                .content(request.getContent())
                .parentComment(parentComment)
                .publication(publication)
                .user(user)
                .build();
        return this.create(comment);
    }
}
