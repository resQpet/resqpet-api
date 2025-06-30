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
import service.domain.dto.chat.BaseChatThreadDTO;
import service.domain.dto.chat.FullChatThreadDTO;
import service.domain.entity.user.User;
import service.domain.request.chat.ChatThreadRequest;
import service.services.chat.ChatThreadService;

@RestController
@AllArgsConstructor
@RequestMapping("/chats/threads")
public class ChatThreadResource {

    private final ChatThreadService service;

    @GetMapping({"/", ""})
    public ResponseEntity<Page<BaseChatThreadDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(pageable).map(BaseChatThreadDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullChatThreadDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(new FullChatThreadDTO(this.service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<BaseChatThreadDTO> create(@Valid @RequestBody ChatThreadRequest request, @Current User user) {
        return ResponseEntity.ok(new BaseChatThreadDTO(this.service.create(user, request)));
    }

}
