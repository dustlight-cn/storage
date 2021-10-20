package cn.dustlight.storage.alibaba.oss;

import cn.dustlight.storage.core.Permission;
import cn.dustlight.storage.core.RestfulStorage;
import cn.dustlight.storage.core.StorableObject;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import org.apache.http.impl.io.EmptyInputStream;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阿里云对象储存器
 */
public class AlibabaCloudObjectStorage implements RestfulStorage {

    OSS oss;
    String bucket;
    ExecutorService pool;

    public AlibabaCloudObjectStorage(OSS oss, String bucket, Integer threadCount) {
        if (threadCount == null)
            threadCount = Runtime.getRuntime().availableProcessors() + 1;
        pool = Executors.newFixedThreadPool(threadCount);
        this.oss = oss;
        this.bucket = bucket;
        if (bucket == null) {
            List<Bucket> buckets = oss.listBuckets();
            if (buckets != null && buckets.size() > 0)
                this.bucket = buckets.get(0).getName();
        }
    }

    @Override
    public StorableObject create(String key, int permission) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setObjectAcl(getACL(permission));
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, EmptyInputStream.INSTANCE, objectMetadata);
        oss.putObject(putObjectRequest);
        return new AlibabaCloudStorableObject(this, key);
    }

    @Override
    public StorableObject get(String key) throws IOException {
        return new AlibabaCloudStorableObject(this, key);
    }

    @Override
    public StorableObject put(String key, StorableObject source) throws IOException {
        return put(key, source, source.getPermission());
    }

    @Override
    public StorableObject put(String key, StorableObject source, int permission) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setObjectAcl(getACL(permission));
        PutObjectRequest req = new PutObjectRequest(bucket, key, source.getInputStream(), objectMetadata);
        oss.putObject(req);
        return new AlibabaCloudStorableObject(this, key);
    }

    @Override
    public void remove(String key) throws IOException {
        oss.deleteObject(bucket, key);
    }

    @Override
    public void setPermission(String key, int permission) throws IOException {
        oss.setObjectAcl(bucket, key, getACL(permission));
    }

    @Override
    public boolean isExist(String key) throws IOException {
        return oss.doesObjectExist(bucket, key);
    }

    @Override
    public String generateGetUrl(String key, Long expiration) throws IOException {
        return oss.generatePresignedUrl(bucket,
                        key,
                        expiration == null ? null : new Date(System.currentTimeMillis() + expiration),
                        HttpMethod.GET)
                .toExternalForm();
    }

    @Override
    public String generatePutUrl(String key, int permission, Long expiration) throws IOException {
        return oss.generatePresignedUrl(bucket,
                        key,
                        expiration == null ? null : new Date(System.currentTimeMillis() + expiration),
                        HttpMethod.PUT)
                .toExternalForm();
    }

    @Override
    public String generateRemoveUrl(String key, Long expiration) throws IOException {
        return oss.generatePresignedUrl(bucket,
                        key,
                        expiration == null ? null : new Date(System.currentTimeMillis() + expiration),
                        HttpMethod.DELETE)
                .toExternalForm();
    }

    @Override
    public String generatePutUrl(String key, int permission, Long expiration, Map<String, String> headers) throws IOException {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.PUT);
        if (headers != null) {
            if (headers.containsKey("Content-Type"))
                generatePresignedUrlRequest.setContentType(headers.get("Content-Type"));
        }
        if (expiration != null)
            generatePresignedUrlRequest.setExpiration(new Date(new Date().getTime() + expiration));
        return oss.generatePresignedUrl(generatePresignedUrlRequest).toExternalForm();
    }

    protected static CannedAccessControlList getACL(int permission) {
        switch (permission) {
            case Permission.PUBLIC:
            case Permission.WRITABLE:
                return CannedAccessControlList.PublicReadWrite;
            case Permission.PRIVATE:
                return CannedAccessControlList.Private;
            case Permission.READABLE:
                return CannedAccessControlList.PublicRead;
            default:
                return CannedAccessControlList.Default;
        }
    }
}
