package cn.dustlight.storage.core;

import java.io.IOException;
import java.util.Map;

public interface UrlStorage {

    String generateGetUrl(String key, Long expiration) throws IOException;

    String generatePutUrl(String key, int permission, Long expiration) throws IOException;

    String generateRemoveUrl(String key, Long expiration) throws IOException;

    String generatePutUrl(String key, int permission, Long expiration, Map<String,String> headers) throws IOException;

}
