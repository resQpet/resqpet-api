package service.resources.foundation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.domain.dto.foundation.FoundationTypeDTO;
import service.domain.spec.foundation.FoundationTypeSpec;
import service.domain.spec.search.foundation.FoundationTypeSearchSpec;
import service.services.foundation.type.FoundationTypeService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "foundations/types")
public class FoundationTypeResource {

    private final FoundationTypeService service;

    @GetMapping
    public ResponseEntity<Page<FoundationTypeDTO>> list(FoundationTypeSpec spec, Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(spec, pageable).map(FoundationTypeDTO::new));
    }

    @GetMapping("search")
    public ResponseEntity<Page<FoundationTypeDTO>> search(FoundationTypeSearchSpec spec, Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(spec, pageable).map(FoundationTypeDTO::new));
    }
}
