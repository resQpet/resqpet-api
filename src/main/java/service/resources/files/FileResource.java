package service.resources.files;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import service.aspect.Current;
import service.domain.entity.file.FileUpload;
import service.domain.entity.user.User;
import service.services.file.FileService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "files")
public class FileResource {

    private final FileService fileService;

    @PostMapping(value = "upload")
    public ResponseEntity<FileUpload> upload(@RequestParam MultipartFile file, @Current User user) {
        final FileUpload authInfo = this.fileService.upload(file, user);
        return ResponseEntity.ok(authInfo);
    }
}