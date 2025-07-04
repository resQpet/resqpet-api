package service.resources.foundation;

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
import service.domain.dto.foundation.BaseFoundationPublicationCommentDTO;
import service.domain.dto.foundation.FoundationPublicationCommentDTO;
import service.domain.entity.user.User;
import service.domain.request.foundation.CommentRequest;
import service.services.foundation.publication.comment.FoundationPublicationCommentService;

@RestController
@AllArgsConstructor
@RequestMapping("/foundations/publications/comments")
public class FoundationPublicationCommentResource {

    private final FoundationPublicationCommentService service;

    @GetMapping("{publicationId}")
    public ResponseEntity<Page<FoundationPublicationCommentDTO>> list(Pageable pageable, @PathVariable Long publicationId) {
        return ResponseEntity.ok(this.service.findAllByPublication(publicationId, pageable).map(FoundationPublicationCommentDTO::new));
    }

    @PostMapping
    public ResponseEntity<BaseFoundationPublicationCommentDTO> create(@Valid @RequestBody CommentRequest request, @Current User user) {
        return ResponseEntity.ok(new BaseFoundationPublicationCommentDTO(this.service.create(request, user)));
    }
}
