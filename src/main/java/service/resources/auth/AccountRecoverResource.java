package service.resources.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.domain.models.ResultResponse;
import service.domain.models.account.AccountRecoverRequest;
import service.domain.models.account.PasswordRecoverRequest;
import service.services.auth.account.AccountService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "account/recover")
public class AccountRecoverResource {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> recover(@RequestBody @Valid PasswordRecoverRequest request) {
        this.accountService.recover(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "send")
    public ResponseEntity<Void> sendAccountRecover(@RequestBody @Valid AccountRecoverRequest request) {
        this.accountService.sendAccountRecover(request.username());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "validate")
    public ResponseEntity<ResultResponse<Boolean>> validateToken(@RequestParam String token) {
        final boolean exists = this.accountService.isRecoverTokenValid(token);
        return ResponseEntity.ok(ResultResponse.of(exists));
    }

}
