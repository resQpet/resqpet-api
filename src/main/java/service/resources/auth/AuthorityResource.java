package service.resources.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.domain.dto.auth.authority.AuthorityDTO;
import service.domain.models.authority.AuthorityRequest;
import service.domain.spec.AuthoritySpec;
import service.services.user.role.authority.AuthorityService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "authorities")
public class AuthorityResource {

    private final AuthorityService authorityService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<AuthorityDTO>> list(AuthoritySpec spec, Pageable pageable) {
        return ResponseEntity.ok(authorityService.findAll(spec, pageable).map(AuthorityDTO::new));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<AuthorityDTO> create(@Valid @RequestBody AuthorityRequest request) {
        return ResponseEntity.ok(new AuthorityDTO(authorityService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorityDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorityRequest request) {
        return ResponseEntity.ok(new AuthorityDTO(authorityService.update(id, request)));
    }

}
