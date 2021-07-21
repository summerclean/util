package work.sihai.common.config.alioss;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

public interface FileService {

    String uploadFile(UploadFileRequest request);

    String uploadFile(String key, InputStream inputStream);

    Map<String, String> uploadPrivateFile(UploadFileRequest request);

    String getPrivateUrl(String key, Date date);
}
