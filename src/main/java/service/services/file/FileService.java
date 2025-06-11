package service.services.file;

import org.springframework.web.multipart.MultipartFile;
import service.domain.entity.file.FileUpload;
import service.domain.entity.user.User;
import service.services.BaseService;

public interface FileService extends BaseService<FileUpload> {

    FileUpload upload(MultipartFile file, User user);

}
