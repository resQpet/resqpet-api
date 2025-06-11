package service.services.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.domain.entity.file.FileUpload;
import service.domain.entity.user.User;
import service.domain.models.s3.UploadResponse;
import service.domain.repository.file.FileUploadRepository;
import service.services.DefaultBaseService;
import service.services.aws.s3.S3Service;
import service.utils.FileUtils;

@Getter
@Service
@AllArgsConstructor
public class DefaultFileService extends DefaultBaseService<FileUpload> implements FileService {

    private final S3Service s3Service;
    private final FileUploadRepository repository;

    @Override
    @Transactional
    public FileUpload upload(MultipartFile file, User user) {
        final String checksum = FileUtils.getChecksum(file);
        final UploadResponse response = this.s3Service.upload(file);
        final FileUpload upload = FileUpload.builder().uploadedBy(user)
                .checksum(checksum)
                .uri(response.uri())
                .build();
        return super.create(upload);
    }

}
