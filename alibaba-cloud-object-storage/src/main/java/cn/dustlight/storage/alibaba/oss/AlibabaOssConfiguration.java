package cn.dustlight.storage.alibaba.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(AlibabaOssProperties.class)
public class AlibabaOssConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.storage.alibaba.oss",
            value = {"access-key-id", "secret-access-key"})
    public AlibabaCloudObjectStorage alibabaStorage(@Autowired AlibabaOssProperties properties) {
        ClientConfiguration configuration = new ClientConfiguration();
        if (properties.getProtocol() != null)
            configuration.setProtocol(properties.getProtocol());
        if (properties.getSupportCname() != null)
            configuration.setSupportCname(properties.getSupportCname());
        OSSClient oss = new OSSClient(properties.getEndpoint(),
                new DefaultCredentialProvider(
                        new DefaultCredentials(properties.getAccessKeyId(),
                                properties.getSecretAccessKey(),
                                properties.getSecurityToken())),
                configuration);
        return new AlibabaCloudObjectStorage(oss, properties.getBucket(), properties.getThreadCount());
    }
}
