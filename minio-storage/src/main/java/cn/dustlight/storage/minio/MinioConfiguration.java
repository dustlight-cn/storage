package cn.dustlight.storage.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.storage.minio",
            value = {"access-key-id", "secret-access-key"})
    public MinioStorage minioStorage(@Autowired MinioProperties properties) {
        MinioClient.Builder builder = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKeyId(), properties.getSecretAccessKey());
        if (StringUtils.hasText(properties.getRegion()))
            builder.region(properties.getRegion());
        MinioClient client = builder.build();
        return new MinioStorage(client, properties.getBucket(), properties.getThreadCount());
    }
}
