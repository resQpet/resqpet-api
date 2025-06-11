package service.services.aws.s3;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import service.domain.models.s3.UploadResponse;
import service.domain.types.aws.FileAccess;

public interface S3Service {

    UploadResponse upload(MultipartFile file);

    String upload(MultipartFile file, @NonNull String filename, @NonNull FileAccess access);

}
