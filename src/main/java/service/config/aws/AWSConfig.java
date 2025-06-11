package service.config.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AWSConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public AwsCredentialsProvider credentials() {
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return StaticCredentialsProvider.create(credentials);
    }

    @Bean
    @DependsOn("credentials")
    public RekognitionClient rekognitionClient(AwsCredentialsProvider awsCredentials) {
        return RekognitionClient.builder().credentialsProvider(awsCredentials).region(Region.US_EAST_1).build();
    }

}