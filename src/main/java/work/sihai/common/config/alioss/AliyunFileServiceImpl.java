package work.sihai.common.config.alioss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AliyunFileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunFileServiceImpl.class);


    @Override
    public String uploadFile(UploadFileRequest request) {
        String fileUrl = "https://" + "bucketname" + "." + "endpoint";
        try {
            String fileName = request.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String key = UUID.randomUUID().toString().replace("-", "") + "." + fileType;
            if (!StringUtils.isEmpty(request.getGroupName())) {
                key = request.getGroupName() + "/" + UUID.randomUUID().toString().replace("-", "") + "." + fileType;
            }
            upload2Oss(key, request.getInputStream());
            fileUrl = fileUrl + "/" + key;
        } catch (Exception e) {
            logger.error("Upload File Error,[groupName:" + request.getGroupName() + " | fileName:" + request.getFileName() + "]", e);
            fileUrl = null;
        }
        return fileUrl;
    }

    @Override
    public String uploadFile(String key, InputStream inputStream) {
        String fileUrl = "https://" + "bucketname" + "." + "endpoint";
        try {
            upload2Oss(key, inputStream);
            fileUrl = fileUrl + "/" + key;
        } catch (Exception e) {
            logger.error("Upload File Error,[key:" + key + "]", e);
            fileUrl = null;
        }
        return fileUrl;
    }

    private String upload2Oss(String key, InputStream inputStream) {
        String bucketName = "bucketname";
        String endpoint = "endpoint";
        //ECS-OSS同区内网加速
        if (endpoint.indexOf("internal") == -1) {
            endpoint = endpoint.replaceFirst("\\.", "-internal.");
        }
        logger.info("createOSSClient endpoint [{}],accessKeyId[{}],accessKeySecret[{}],bucketName[{}] ",
                endpoint, "key",
                "secret", bucketName);
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxConnections(10);
        configuration.setSocketTimeout(10);
        configuration.setMaxErrorRetry(10);
        configuration.setSupportCname(true);
        OSSClient ossClient = new OSSClient(endpoint, "key",
                "secret", configuration);
        ossClient.putObject(bucketName, key, inputStream);
        //设置文件权限
        ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Private);
        ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Default);
        Date date = DateUtils.addYears(new Date(), 3);
        String presignedUrl = ossClient.generatePresignedUrl(bucketName, key, date).toString();
        logger.info("生成文件签名URL:{}", presignedUrl);
        ossClient.shutdown();
        return presignedUrl;
    }

    @Override
    public Map<String, String> uploadPrivateFile(UploadFileRequest request) {
        Map<String, String> result = new HashMap<>();
        try {
            String fileName = request.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String key = request.getGroupName() + "/" + UUID.randomUUID().toString().replace("-", "") + "." + fileType;
            OSSClient ossClient = getOssClient();
            ossClient.putObject("bucketname", key, request.getInputStream());
            ossClient.setObjectAcl("bucketname", key, CannedAccessControlList.Private);
            String url = getPrivateUrl(key, new Date());
            result.put("fileName", fileName);
            result.put("fileType", fileType);
            result.put("key", key);
            result.put("url", url);
        } catch (Exception e) {
            logger.error("Upload File Error,[groupName:" + request.getGroupName() + " | fileName:" + request.getFileName() + "]", e);
            return result;
        }
        return result;
    }

    @Override
    public String getPrivateUrl(String key, Date date) {
        if (StringUtils.isEmpty(key) || null == date) {
            return null;
        }
        OSSClient ossClient = getOssClient();
        if (null == ossClient) {
            return null;
        }
        URL url = ossClient.generatePresignedUrl("bucketname", key, date);
        return url.toString();

    }

    private OSSClient getOssClient() {
        logger.info("createOSSClient endpoint [{}],accessKeyId[{}],accessKeySecret[{}],bucketName[{}] ",
                "endpoint", "key",
                "secret", "bucketname");
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxConnections(10);
        configuration.setSocketTimeout(10);
        configuration.setMaxErrorRetry(10);
        configuration.setSupportCname(true);
        OSSClient ossClient = new OSSClient("endpoint",
                "key", "secret",
                configuration);
        return ossClient;

    }
}
