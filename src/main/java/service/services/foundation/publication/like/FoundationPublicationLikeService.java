package service.services.foundation.publication.like;

import service.domain.entity.foundation.FoundationPublicationLike;
import service.domain.entity.user.User;
import service.services.BaseService;

public interface FoundationPublicationLikeService extends BaseService<FoundationPublicationLike> {

    void like(Long publicationId, User user);

    void unlike(Long publicationId, User user);

    boolean hasLike(Long publicationId, User user);

    long countLikes(Long publicationId);

}
