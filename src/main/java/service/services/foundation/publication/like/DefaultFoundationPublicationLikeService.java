package service.services.foundation.publication.like;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.FoundationPublication;
import service.domain.entity.foundation.FoundationPublicationLike;
import service.domain.entity.user.User;
import service.domain.repository.foundation.FoundationPublicationLikeRepository;
import service.services.DefaultBaseService;
import service.services.foundation.publication.FoundationPublicationService;

import java.util.Optional;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationPublicationLikeService extends DefaultBaseService<FoundationPublicationLike> implements FoundationPublicationLikeService {

    private final FoundationPublicationLikeRepository repository;
    private final FoundationPublicationService publicationService;

    /**
     * Adds a like to the specified publication by the given user. If the user has
     * already liked the publication and the like was previously marked as deleted,
     * this method will re-activate the like. If no like exists from the user, a new
     * like will be created and associated with the publication.
     *
     * @param publicationId the ID of the publication to be liked
     * @param user          the user who likes the publication
     */
    @Override
    @Transactional
    public void like(Long publicationId, User user) {
        final FoundationPublication publication = this.publicationService.findById(publicationId);
        final Optional<FoundationPublicationLike> optionalLike = this.repository.findByPublicationAndUser(publication, user);

        if (optionalLike.isPresent()) {
            final FoundationPublicationLike like = optionalLike.get();
            if (Boolean.TRUE.equals(like.getDeleted())) {
                like.setDeleted(Boolean.FALSE);
                this.save(like);
            }
        } else {
            final FoundationPublicationLike like = FoundationPublicationLike.builder()
                    .publication(publication)
                    .user(user)
                    .deleted(Boolean.FALSE)
                    .build();
            this.create(like);
        }
    }

    /**
     * Removes a like from the specified publication for the given user. If the user has
     * previously liked the publication and the like is not marked as deleted, this method
     * marks the like as deleted.
     *
     * @param publicationId the ID of the publication whose like is to be removed
     * @param user          the user who is removing their like from the publication
     */
    @Override
    @Transactional
    public void unlike(Long publicationId, User user) {
        final FoundationPublication publication = this.publicationService.findById(publicationId);
        this.repository.findByPublicationAndUserAndDeletedFalse(publication, user)
                .ifPresent(like -> {
                    like.setDeleted(Boolean.TRUE);
                    this.save(like);
                });
    }

    /**
     * Checks if a given user has liked the specified publication.
     *
     * @param publicationId the ID of the publication to check for a user's like
     * @param user          the user whose like is being checked
     * @return true if the user has liked the publication, false otherwise
     */
    @Override
    public boolean hasLike(Long publicationId, User user) {
        final FoundationPublication publication = this.publicationService.findById(publicationId);
        return this.repository.findByPublicationAndUserAndDeletedFalse(publication, user).isPresent();
    }

    /**
     * Counts the total number of likes for a specific publication that are not marked as deleted.
     *
     * @param publicationId the ID of the publication whose likes are being counted
     * @return the total number of active (non-deleted) likes for the specified publication
     */
    @Override
    public long countLikes(Long publicationId) {
        final FoundationPublication publication = this.publicationService.findById(publicationId);
        return this.repository.countByPublicationAndDeletedFalse(publication);
    }

}
