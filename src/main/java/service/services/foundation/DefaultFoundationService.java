package service.services.foundation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.Foundation;
import service.domain.repository.foundation.FoundationRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationService extends DefaultBaseService<Foundation> implements FoundationService {

    private final FoundationRepository repository;

}
