package service.domain.models.s3;

import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public record UploadResponse(PutObjectResponse response, String bucket, String name, String uri) {
}
