package service.services.foundation.social;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.FoundationSocial;
import service.domain.repository.foundation.FoundationSocialRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationSocialService extends DefaultBaseService<FoundationSocial> implements FoundationSocialService {

    private final FoundationSocialRepository repository;

}
