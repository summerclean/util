package work.sihai.common.config.alioss;

import io.swagger.annotations.ApiModelProperty;

import java.io.File;
import java.io.InputStream;

public class UploadFileRequest {

    /**
     * 文件分组名称
     * eg:阿里云文件分组目录, http://common.oss-cn-hangzhou.aliyuncs.com/{groupName}/c5c31026.png
     */
    @ApiModelProperty(value = "文件分组名称")
    private String groupName;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String type;

    /**
     * 文件数据
     */
    @ApiModelProperty(value = "文件数据")
    private byte[] fileData;

    /**
     * 内容类型
     */
    @ApiModelProperty(value = "内容类型")
    private String contentType;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;

    /**
     * 文件数据流
     */
    @ApiModelProperty(value = "文件数据流")
    private InputStream inputStream;

    /**
     * 文件
     */
    @ApiModelProperty(value = "file")
    private File file;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
