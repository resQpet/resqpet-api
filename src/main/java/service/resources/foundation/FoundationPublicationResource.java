package service.resources.foundation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.domain.dto.foundation.FoundationPublicationDTO;
import service.services.foundation.publication.FoundationPublicationService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "foundations/publications")
public class FoundationPublicationResource {

    private final FoundationPublicationService service;

    @GetMapping({"/", ""})
    public ResponseEntity<Page<FoundationPublicationDTO>> getNearbyPublications(Pageable pageable) {
        return ResponseEntity.ok(service.findNearbyPublications(pageable).map(FoundationPublicationDTO::new));
    }

}
