package service.resources.location;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.context.LocationContext;
import service.domain.dto.location.CurrentLocation;

@RestController
@AllArgsConstructor
@RequestMapping("/locations")
public class TestLocationController {

    @GetMapping
    public ResponseEntity<CurrentLocation> getLocation() {
        final CurrentLocation location = LocationContext.get();
        return ResponseEntity.ok(location);
    }

}
