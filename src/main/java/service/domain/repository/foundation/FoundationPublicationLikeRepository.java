package service.domain.repository.foundation;

import service.domain.entity.foundation.FoundationPublication;
import service.domain.entity.foundation.FoundationPublicationLike;
import service.domain.entity.user.User;
import service.domain.repository.BaseRepository;

import java.util.Optional;

public interface FoundationPublicationLikeRepository extends BaseRepository<FoundationPublicationLike> {

    Optional<FoundationPublicationLike> findByPublicationAndUserAndDeletedFalse(FoundationPublication publication, User user);

    long countByPublicationAndDeletedFalse(FoundationPublication publication);

    Optional<FoundationPublicationLike> findByPublicationAndUser(FoundationPublication publication, User user);

}
