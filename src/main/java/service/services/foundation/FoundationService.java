package service.services.foundation;

import service.domain.entity.foundation.Foundation;
import service.domain.entity.user.User;
import service.domain.request.foundation.FoundationRequest;
import service.services.BaseService;

public interface FoundationService extends BaseService<Foundation> {

    Foundation create(FoundationRequest request, User user);

}
