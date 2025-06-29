package service.services.foundation.evidence;

import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationEvidence;
import service.domain.request.foundation.FoundationEvidenceRequest;
import service.services.BaseService;

public interface FoundationEvidenceService extends BaseService<FoundationEvidence> {

    void create(Foundation foundation, FoundationEvidenceRequest evidence);

}
