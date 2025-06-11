package service.domain.types.aws;

import lombok.Getter;
import software.amazon.awssdk.services.s3.model.StorageClass;

@Getter
public enum FileAccess {
    STANDARD(StorageClass.STANDARD_IA),
    FREQUENT(StorageClass.STANDARD);

    private final StorageClass storageClass;

    FileAccess(StorageClass storageClass) {
        this.storageClass = storageClass;
    }
}
