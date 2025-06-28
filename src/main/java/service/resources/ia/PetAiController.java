package service.resources.ia;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.domain.dto.ia.PetChatRequest;
import service.domain.request.pet.PetChatResponse;
import service.services.ia.chat.ChatService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/ia")
public class PetAiController {

    private final ChatService service;

    @PostMapping
    public ResponseEntity<PetChatResponse> chat(@RequestBody PetChatRequest request) {
        PetChatResponse response = this.service.handleChat(request);
        return ResponseEntity.ok(response);
    }
}
