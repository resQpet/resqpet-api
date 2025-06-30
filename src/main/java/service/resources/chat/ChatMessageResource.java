package service.resources.chat;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.aspect.Current;
import service.domain.dto.chat.ChatMessageDTO;
import service.domain.entity.user.User;
import service.domain.request.chat.ChatMessageRequest;
import service.services.chat.message.ChatMessageService;

@RestController
@AllArgsConstructor
@RequestMapping("/chats/messages")
public class ChatMessageResource {

    private final ChatMessageService service;

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<Page<ChatMessageDTO>> listByThread(@PathVariable Long threadId, Pageable pageable) {
        return ResponseEntity.ok(this.service.findByThreadId(threadId, pageable).map(ChatMessageDTO::new));
    }

    @PostMapping
    public ResponseEntity<ChatMessageDTO> create(@Valid @RequestBody ChatMessageRequest request, @Current User user) {
        return ResponseEntity.ok(new ChatMessageDTO(this.service.create(request, user)));
    }
}
