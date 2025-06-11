package service.config.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.s3")
public class S3Config {

    private String bucket;

    @Bean
    public S3AsyncClient asyncClient(AwsCredentialsProvider awsCredentials) {
        return S3AsyncClient.builder()
                .credentialsProvider(awsCredentials)
                .region(Region.US_EAST_2)
                .build();
    }

    @Bean
    @Primary
    public S3Client s3Client(AwsCredentialsProvider awsCredentials) {
        return S3Client
                .builder()
                .credentialsProvider(awsCredentials)
                .region(Region.US_EAST_2)
                .build();
    }

    public String getKey(String filename) {
        return String.format("%s/%s", "users", filename);
    }

    public String getURI(String destination) {
        return String.format("https://s3.us-east-2.amazonaws.com/%s/%s", bucket, destination);
    }
}