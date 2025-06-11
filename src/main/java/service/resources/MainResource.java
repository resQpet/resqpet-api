package service.resources;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.config.api.APIInfo;

@RestController
@AllArgsConstructor
public class MainResource {

    private final APIInfo info;

    @GetMapping
    public ResponseEntity<APIInfo> get() {
        return ResponseEntity.ok(info);
    }
}
