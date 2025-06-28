package service.services.foundation.publication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.domain.entity.foundation.FoundationPublication;
import service.services.BaseService;

public interface FoundationPublicationService extends BaseService<FoundationPublication> {

    Page<FoundationPublication> findNearbyPublications(Pageable pageable);

}
