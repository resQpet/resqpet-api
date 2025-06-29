package service.services.foundation.evidence;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationEvidence;
import service.domain.repository.foundation.FoundationEvidenceRepository;
import service.domain.request.foundation.FoundationEvidenceRequest;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationEvidenceService extends DefaultBaseService<FoundationEvidence> implements FoundationEvidenceService {

    private final FoundationEvidenceRepository repository;

    /**
     * Creates a new {@link FoundationEvidence} entity by mapping the provided {@link FoundationEvidenceRequest},
     * then delegates to the base service for persistence.
     *
     * @param foundation the foundation associated with the evidence
     * @param evidence   the evidence request containing the file URL and file type for the new evidence entity
     */
    @Override
    @Transactional
    public void create(Foundation foundation, FoundationEvidenceRequest evidence) {
        final FoundationEvidence foundationEvidence = FoundationEvidence.builder()
                .fileUrl(evidence.getFileUrl())
                .fileType(evidence.getFileType())
                .foundation(foundation)
                .build();
        this.create(foundationEvidence);
    }
}
