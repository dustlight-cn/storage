package cn.dustlight.storage.minio;

import cn.dustlight.storage.core.StorableObject;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;

import java.io.*;

public class MinioStorableObject implements StorableObject {

    private MinioStorage storage;
    private String key;

    MinioStorableObject(MinioStorage storage, String key) {
        this.storage = storage;
        this.key = key;
    }

    @SneakyThrows
    @Override
    public InputStream getInputStream() throws IOException {
        return storage.client.getObject(GetObjectArgs.builder().bucket(storage.bucket).object(key).build());
    }

    @SneakyThrows
    @Override
    public OutputStream getOutputStream() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        outputStream.connect(inputStream);
        storage.pool.execute(new PipedStreamTask(inputStream));
        return outputStream;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int getPermission() {
        return 0;
    }

    protected class PipedStreamTask implements Runnable {

        private InputStream inputStream;

        PipedStreamTask(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @SneakyThrows
        @Override
        public void run() {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int len;
            try (BufferedInputStream bin = new BufferedInputStream(inputStream)) {
                while ((len = bin.read(buffer, 0, buffer.length)) != -1) {
                    temp.write(buffer, 0, len);
                }
                storage.client.putObject(PutObjectArgs.builder()
                        .bucket(storage.bucket)
                        .object(key)
                        .stream(new ByteArrayInputStream(temp.toByteArray()), temp.size(), 0)
                        .build());
            }
        }
    }
}
