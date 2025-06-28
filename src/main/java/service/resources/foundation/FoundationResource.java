package service.resources.foundation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.services.foundation.FoundationService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/foundations")
public class FoundationResource {

    private final FoundationService service;

}
