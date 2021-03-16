package cn.dustlight.storage.alibaba.oss;

import com.aliyun.oss.common.comm.Protocol;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dustlight.storage.alibaba.oss")
public class AlibabaOssProperties {

    private String accessKeyId;
    private String secretAccessKey;
    private String securityToken;
    private String bucket;
    /**
     * 参考：https://help.aliyun.com/document_detail/31837.html
     */
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private Protocol protocol = Protocol.HTTPS;
    private Boolean supportCname;
    private Integer threadCount;


    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public Boolean getSupportCname() {
        return supportCname;
    }

    public void setSupportCname(Boolean supportCname) {
        this.supportCname = supportCname;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }
}
