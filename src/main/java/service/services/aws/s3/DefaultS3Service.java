package service.services.aws.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.config.aws.S3Config;
import service.domain.exceptions.InternalErrorException;
import service.domain.models.s3.UploadResponse;
import service.domain.types.aws.FileAccess;
import service.utils.FileUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.StorageClass;

@Slf4j
@Service
public class DefaultS3Service implements S3Service {

    private final S3Client client;
    private final S3Config config;

    public DefaultS3Service(S3Config s3Config, S3Client client) {
        this.config = s3Config;
        this.client = client;
    }

    /**
     * Uploads a file to the specified S3 bucket with the necessary metadata.
     *
     * @param file the MultipartFile object to upload
     * @return an UploadResponse containing information about the uploaded file
     */
    @Override
    public UploadResponse upload(MultipartFile file) {
        try {
            final String bucket = config.getBucket();
            final String filename = config.getKey(FileUtils.getRandomName(file));
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .storageClass(StorageClass.STANDARD)
                    .build();

            final byte[] bytes = file.getInputStream().readAllBytes();
            final PutObjectResponse response = client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
            final String uri = config.getURI(filename);
            return new UploadResponse(response, bucket, filename, uri);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException(e.getMessage());
        }
    }

    /**
     * Uploads a file to the specified S3 bucket with the given filename and access permissions.
     *
     * @param file the file to upload
     * @param filename the desired filename in the S3 bucket
     * @param access the access permissions for the uploaded file
     * @return the URI of the uploaded file
     */
    @Override
    public String upload(MultipartFile file, @NonNull String filename, @NonNull FileAccess access) {
        try {
            final String bucket = config.getBucket();
            final String destination = config.getKey(filename);
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .key(destination)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .storageClass(access.getStorageClass())
                    .build();

            client.putObject(putObjectRequest, RequestBody.fromBytes(file.getInputStream().readAllBytes()));
            return config.getURI(destination);
        } catch (Throwable e) {
            return e.getMessage();
        }
    }
}
