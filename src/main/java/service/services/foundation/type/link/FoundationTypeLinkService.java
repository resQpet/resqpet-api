package service.services.foundation.type.link;

import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationTypeLink;
import service.services.BaseService;

import java.util.List;

public interface FoundationTypeLinkService extends BaseService<FoundationTypeLink> {

    void create(Foundation foundation, List<Long> foundationTypeIds);

}
