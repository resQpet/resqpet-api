package service.services.foundation.publication.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.foundation.FoundationPublicationImage;
import service.domain.repository.foundation.FoundationPublicationImageRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationPublicationImageService extends DefaultBaseService<FoundationPublicationImage> implements FoundationPublicationImageService {

    private final FoundationPublicationImageRepository repository;

}
