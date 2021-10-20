package cn.dustlight.storage.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "dustlight.storage.minio")
public class MinioProperties {

    private String accessKeyId;
    private String secretAccessKey;
    private String bucket;
    private String endpoint;
    private String region;
    private Integer threadCount;

}
