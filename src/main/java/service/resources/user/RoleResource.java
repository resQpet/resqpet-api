package service.resources.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.domain.dto.auth.RoleDTO;
import service.domain.models.role.RoleRequest;
import service.domain.spec.RoleSpec;
import service.domain.spec.search.RoleSearchSpec;
import service.services.user.role.RoleService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping(value = "roles")
public class RoleResource {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> list(RoleSpec spec, Pageable pageable) {
        return ok(this.roleService.findAll(spec, pageable).map(RoleDTO::new));
    }

    @GetMapping("search")
    public ResponseEntity<Page<RoleDTO>> search(RoleSearchSpec spec, Pageable pageable) {
        return ok(this.roleService.findAll(spec, pageable).map(RoleDTO::new));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody @Valid RoleRequest request) {
        return ok(RoleDTO.of(this.roleService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody @Valid RoleRequest role) {
        return ok(RoleDTO.of(this.roleService.update(id, role)));
    }

    @PostMapping("/{roleId}/assign/{authorityId}")
    public ResponseEntity<RoleDTO> assignAuthorityToRole(@PathVariable Long roleId, @PathVariable Long authorityId) {
        return ResponseEntity.ok(new RoleDTO(roleService.assign(roleId, authorityId)));
    }

}
