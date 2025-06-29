package service.services.foundation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.FoundationType;
import service.domain.repository.foundation.FoundationTypeRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationTypeService extends DefaultBaseService<FoundationType> implements FoundationTypeService {

    private final FoundationTypeRepository repository;

}
