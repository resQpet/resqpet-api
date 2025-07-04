package service.domain.repository.foundation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.domain.entity.foundation.FoundationPublication;
import service.domain.entity.foundation.FoundationPublicationComment;
import service.domain.repository.BaseRepository;

public interface FoundationPublicationCommentRepository extends BaseRepository<FoundationPublicationComment> {

    Page<FoundationPublicationComment> findAllByPublication(FoundationPublication publication, Pageable pageable);

}
