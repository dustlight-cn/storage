package cn.dustlight.storage.alibaba.oss;

import cn.dustlight.storage.core.Permission;
import cn.dustlight.storage.core.StorableObject;
import com.aliyun.oss.model.*;

import java.io.*;

public class AlibabaCloudStorableObject implements StorableObject {

    private OSSObject ossObject;
    private AlibabaCloudObjectStorage storage;
    private String key;

    AlibabaCloudStorableObject(AlibabaCloudObjectStorage storage, String key) {
        this.storage = storage;
        this.key = key;
        this.ossObject = storage.oss.getObject(storage.bucket, key);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return ossObject.getObjectContent();
    }

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
        ObjectAcl acl = storage.oss.getObjectAcl(storage.bucket, key);
        ObjectPermission objectPermission = acl.getPermission();
        if (objectPermission == ObjectPermission.Default)
            return getPermission(storage.oss.getBucketAcl(storage.bucket).getCannedACL());
        return getPermission(objectPermission);
    }

    protected static int getPermission(CannedAccessControlList cannedAccessControlList) {
        switch (cannedAccessControlList) {
            case PublicRead:
                return Permission.READABLE;
            case PublicReadWrite:
                return Permission.PUBLIC;
            case Default:
            case Private:
            default:
                return Permission.PRIVATE;
        }
    }

    protected static int getPermission(ObjectPermission objectPermission) {
        switch (objectPermission) {
            case PublicRead:
                return Permission.READABLE;
            case PublicReadWrite:
                return Permission.PUBLIC;
            case Default:
            case Private:
            default:
                return Permission.PRIVATE;
        }
    }

    protected class PipedStreamTask implements Runnable {

        private InputStream inputStream;

        PipedStreamTask(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            storage.oss.putObject(storage.bucket, key, inputStream, null);
        }
    }
}
