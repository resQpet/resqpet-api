package service.services.foundation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.user.User;
import service.domain.exceptions.DuplicateException;
import service.domain.repository.foundation.FoundationRepository;
import service.domain.request.foundation.FoundationRequest;
import service.services.DefaultBaseService;
import service.services.foundation.evidence.FoundationEvidenceService;
import service.services.foundation.location.FoundationLocationService;
import service.services.foundation.role.FoundationUserRoleService;
import service.services.foundation.type.link.FoundationTypeLinkService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationService extends DefaultBaseService<Foundation> implements FoundationService {

    private final FoundationRepository repository;
    private final FoundationLocationService locationService;
    private final FoundationTypeLinkService foundationTypeLinkService;
    private final FoundationEvidenceService evidenceService;
    private final FoundationUserRoleService foundationUserRoleService;

    /**
     * Creates a new Foundation entity based on the provided FoundationRequest and User.
     *
     * @param request the request containing the details needed to create the Foundation, including name, founded date,
     *                email, phone, website, foundation types, location, and evidence
     * @param user    the user who is creating the Foundation, used for associating the foundation with a user role
     * @return the created Foundation entity
     */
    @Override
    @Transactional
    public Foundation create(FoundationRequest request, User user) {
        validateIfExistsByName(request);
        final Foundation foundation = Foundation.builder()
                .name(request.getName())
                .foundedDate(request.getFoundedDate())
                .email(request.getEmail())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .build();
        this.create(foundation);

        this.locationService.create(foundation, request.getLocation());
        this.foundationTypeLinkService.create(foundation, request.getFoundationTypeIds());
        this.evidenceService.create(foundation, request.getEvidence());
        this.foundationUserRoleService.create(foundation, user);
        return foundation;
    }

    private void validateIfExistsByName(FoundationRequest request) {
        if (this.repository.existsByName(request.getName())) {
            throw new DuplicateException("Foundation with name " + request.getName() + " already exists.");
        }
    }
}
