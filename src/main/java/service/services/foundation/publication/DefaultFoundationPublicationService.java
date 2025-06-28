package service.services.foundation.publication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.context.LocationContext;
import service.domain.entity.foundation.FoundationPublication;
import service.domain.repository.foundation.FoundationPublicationRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationPublicationService extends DefaultBaseService<FoundationPublication> implements FoundationPublicationService {

    private final FoundationPublicationRepository repository;

    public Page<FoundationPublication> findNearbyPublications(Pageable pageable) {
        return this.repository.findNearbyPublications(LocationContext.get().getLatitude(), LocationContext.get().getLongitude(), pageable);
    }

}
