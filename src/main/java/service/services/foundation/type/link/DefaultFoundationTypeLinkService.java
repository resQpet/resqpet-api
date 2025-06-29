package service.services.foundation.type.link;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationType;
import service.domain.entity.foundation.FoundationTypeLink;
import service.domain.repository.foundation.FoundationTypeLinkRepository;
import service.services.DefaultBaseService;
import service.services.foundation.type.FoundationTypeService;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationTypeLinkService extends DefaultBaseService<FoundationTypeLink> implements FoundationTypeLinkService {

    private final FoundationTypeLinkRepository repository;
    private final FoundationTypeService foundationTypeService;

    /**
     * Creates and persists links between the provided foundation entity and the list of foundation type IDs.
     * For each foundation type ID in the list, a corresponding {@link FoundationTypeLink} entity is created
     * and stored in the repository.
     *
     * @param foundation        the foundation entity for which the type links are being created
     * @param foundationTypeIds a list of IDs representing the foundation types to be linked to the foundation
     */
    @Override
    @Transactional
    public void create(Foundation foundation, List<Long> foundationTypeIds) {
        for (final Long foundationTypeId : foundationTypeIds) {
            final FoundationType foundationType = this.foundationTypeService.findById(foundationTypeId);
            final FoundationTypeLink foundationTypeLink = FoundationTypeLink.builder()
                    .foundation(foundation)
                    .type(foundationType)
                    .build();
            this.create(foundationTypeLink);
        }
    }
}
