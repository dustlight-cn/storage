package cn.dustlight.storage.minio;

import cn.dustlight.storage.core.RestfulStorage;
import cn.dustlight.storage.core.StorableObject;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MinioStorage implements RestfulStorage {

    MinioClient client;
    String bucket;
    ExecutorService pool;

    @SneakyThrows
    public MinioStorage(MinioClient client, String bucket, Integer threadCount) {
        this.client = client;
        this.bucket = bucket;

        if (threadCount == null)
            threadCount = Runtime.getRuntime().availableProcessors() + 1;
        pool = Executors.newFixedThreadPool(threadCount);

        if (!StringUtils.hasText(this.bucket)) {
            List<Bucket> buckets = client.listBuckets();
            if (buckets != null && !buckets.isEmpty())
                this.bucket = buckets.get(0).name();
        }
    }

    @Override
    @SneakyThrows
    public StorableObject create(String key, int permission) throws IOException {
        PutObjectArgs p = PutObjectArgs.builder().bucket(bucket).object(key).build();
        client.putObject(p);
        return new MinioStorableObject(this, key);
    }

    @Override
    public StorableObject get(String key) throws IOException {
        return new MinioStorableObject(this, key);
    }

    @Override
    public StorableObject put(String key, StorableObject source) throws IOException {
        return put(key, source, source.getPermission());
    }

    @SneakyThrows
    @Override
    public StorableObject put(String key, StorableObject source, int permission) throws IOException {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        try (InputStream in = new BufferedInputStream(source.getInputStream())) {
            byte[] buffer = new byte[512];
            int len;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                temp.write(buffer, 0, len);
            }
            PutObjectArgs p = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .stream(new ByteArrayInputStream(temp.toByteArray()), temp.size(), 0)
                    .build();
            client.putObject(p);
            return new MinioStorableObject(this, key);
        }
    }

    @SneakyThrows
    @Override
    public void remove(String key) throws IOException {
        client.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(key).build());
    }

    @Override
    public void setPermission(String key, int permission) throws IOException {
        throw new UnsupportedOperationException("Minio storage not support this method yet.");
    }

    @SneakyThrows
    @Override
    public boolean isExist(String key) throws IOException {
        try (GetObjectResponse obj = client.getObject(GetObjectArgs.builder().bucket(bucket).object(key).build())) {
            return true;
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404)
                return false;
            throw e;
        }
    }

    @SneakyThrows
    @Override
    public String generateGetUrl(String key, Long expiration) throws IOException {
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(key);
        if (expiration != null)
            builder.expiry((int) (expiration / 1000));
        return client.getPresignedObjectUrl(builder.build());
    }

    @SneakyThrows
    @Override
    public String generatePutUrl(String key, int permission, Long expiration) throws IOException {
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucket)
                .object(key);
        if (expiration != null)
            builder.expiry((int) (expiration / 1000));
        return client.getPresignedObjectUrl(builder.build());
    }

    @SneakyThrows
    @Override
    public String generateRemoveUrl(String key, Long expiration) throws IOException {
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder()
                .method(Method.DELETE)
                .bucket(bucket)
                .object(key);
        if (expiration != null)
            builder.expiry((int) (expiration / 1000));
        return client.getPresignedObjectUrl(builder.build());
    }

    @SneakyThrows
    @Override
    public String generatePutUrl(String key, int permission, Long expiration, Map<String, String> headers) throws IOException {
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucket)
                .object(key);
        if (expiration != null)
            builder.expiry((int) (expiration / 1000));
        if (headers != null)
            builder.extraHeaders(headers);
        return client.getPresignedObjectUrl(builder.build());
    }
}
