package service.resources.foundation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.aspect.Current;
import service.domain.entity.user.User;
import service.domain.models.ResultResponse;
import service.services.foundation.publication.like.FoundationPublicationLikeService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "publications/{publicationId}/likes")
public class FoundationPublicationLikeResource {

    private FoundationPublicationLikeService likeService;

    @GetMapping
    public ResponseEntity<ResultResponse<Boolean>> hasLike(@PathVariable Long publicationId, @Current User user) {
        final boolean hasLike = this.likeService.hasLike(publicationId, user);
        return ResponseEntity.ok().body(new ResultResponse<>(hasLike));
    }

    @GetMapping("/count")
    public ResponseEntity<ResultResponse<Long>> countLikes(@PathVariable Long publicationId) {
        final long count = this.likeService.countLikes(publicationId);
        return ResponseEntity.ok().body(new ResultResponse<>(count));
    }

    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long publicationId, @Current User user) {
        this.likeService.like(publicationId, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> unlike(@PathVariable Long publicationId, @Current User user) {
        this.likeService.unlike(publicationId, user);
        return ResponseEntity.ok().build();
    }

}
