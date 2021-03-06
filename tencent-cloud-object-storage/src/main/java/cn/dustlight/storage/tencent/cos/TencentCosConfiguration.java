package cn.dustlight.storage.tencent.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(TencentCosProperties.class)
public class TencentCosConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "dustlight.storage.tencent.cos",
            value = {"secret-id", "secret-key"})
    public TencentCloudObjectStorage tencentStorage(@Autowired TencentCosProperties properties) {
        COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        Region region = new Region(properties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(properties.getHttpProtocol());
        clientConfig.setEndpointBuilder(new TencentCosEndpointBuilder(properties.getGeneralApi(), properties.getServiceApi(), clientConfig.getEndpointBuilder()));
        COSClient cosClient = new COSClient(cred, clientConfig);
        TencentCloudObjectStorage tencentCloudObjectStorage = new TencentCloudObjectStorage(cosClient, properties.getBucket(), properties.getThreadCount());
        return tencentCloudObjectStorage;
    }
}
