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
import service.aspect.Current;
import service.domain.dto.users.BaseUserDTO;
import service.domain.entity.user.User;
import service.domain.models.user.UserRequest;
import service.domain.spec.UserSpec;
import service.domain.spec.search.UserSearchSpec;
import service.domain.types.UserStatus;
import service.services.user.UserService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class UserResource {

    private final UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<BaseUserDTO>> list(UserSpec spec, Pageable pageable) {
        return ok(this.userService.findAll(spec, pageable).map(BaseUserDTO::new));
    }

    @GetMapping("search")
    public ResponseEntity<Page<BaseUserDTO>> search(UserSearchSpec spec, Pageable pageable) {
        return ok(this.userService.findAll(spec, pageable).map(BaseUserDTO::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseUserDTO> getOne(@PathVariable Long id) {
        final User user = this.userService.findById(id);
        return ok(new BaseUserDTO(user));
    }

    @GetMapping("document/{document}")
    public ResponseEntity<BaseUserDTO> validate(@PathVariable String document) {
        final User user = this.userService.findByDocument(document);
        return ok(new BaseUserDTO(user));
    }

    @GetMapping("current")
    public ResponseEntity<BaseUserDTO> getCurrent(@Current User user) {
        return ok(new BaseUserDTO(user));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseUserDTO> create(@Valid @RequestBody UserRequest request) {
        final User created = this.userService.create(request);
        return ok(new BaseUserDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseUserDTO> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        final User updated = this.userService.update(id, request);
        return ok(new BaseUserDTO(updated));
    }

    @PutMapping("{id}/status/{status}")
    public ResponseEntity<BaseUserDTO> updateStatus(@PathVariable Long id, @PathVariable UserStatus status) {
        final User updated = this.userService.statusChange(id, status);
        return ok(new BaseUserDTO(updated));
    }

}
