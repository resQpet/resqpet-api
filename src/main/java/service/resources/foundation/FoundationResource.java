package service.resources.foundation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.aspect.Current;
import service.domain.dto.foundation.BaseFoundationDTO;
import service.domain.dto.foundation.SimpleFoundationDTO;
import service.domain.entity.user.User;
import service.domain.request.foundation.FoundationRequest;
import service.domain.spec.foundation.FoundationSpec;
import service.domain.spec.search.foundation.FoundationSearchSpec;
import service.services.foundation.FoundationService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/foundations")
public class FoundationResource {

    private final FoundationService service;

    @GetMapping
    public ResponseEntity<Page<SimpleFoundationDTO>> list(FoundationSpec spec, Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(spec, pageable).map(SimpleFoundationDTO::new));
    }

    @GetMapping("search")
    public ResponseEntity<Page<SimpleFoundationDTO>> search(FoundationSearchSpec spec, Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(spec, pageable).map(SimpleFoundationDTO::new));
    }

    @PostMapping("register")
    public ResponseEntity<BaseFoundationDTO> create(@Valid @RequestBody FoundationRequest request, @Current User user) {
        return ResponseEntity.ok(new BaseFoundationDTO(this.service.create(request, user)));
    }

}
