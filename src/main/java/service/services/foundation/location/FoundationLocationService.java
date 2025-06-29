package service.services.foundation.location;

import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationLocation;
import service.domain.request.foundation.FoundationLocationRequest;
import service.services.BaseService;

import java.util.List;

public interface FoundationLocationService extends BaseService<FoundationLocation> {

    void create(Foundation foundation, List<FoundationLocationRequest> location);

}
