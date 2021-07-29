//package work.sihai.common.config.mybatis;
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableLogic;
//import com.baomidou.mybatisplus.annotation.Version;
//import com.baomidou.mybatisplus.extension.activerecord.Model;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.util.Date;
//
//public class BaseBizEntity<T extends Model> extends BaseEntity<T> {
//
//    /**
//     * 创建者
//     */
//    @ApiModelProperty(value = "创建者")
//    @TableField(value = "create_by", fill = FieldFill.INSERT)
//    @JsonIgnore
//    protected Long createBy;
//
//    /**
//     * 创建日期
//     */
//    @ApiModelProperty(value = "创建日期")
//    @TableField(value = "create_date", fill = FieldFill.INSERT)
//    @JsonIgnore
//    protected Date createDate;
//
//    /**
//     * 更新者
//     */
//    @ApiModelProperty(value = "更新者")
//    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
//    @JsonIgnore
//    protected Long updateBy;
//
//    /**
//     * 更新日期
//     */
//    @ApiModelProperty(value = "更新日期")
//    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
//    @JsonIgnore
//    protected Date updateDate;
//
//    /**
//     * 删除标记（0：正常；1：删除；）
//     */
//    @ApiModelProperty(value = "删除标记", hidden = true)
//    @TableLogic
//    @TableField(value = "deleted")
//    @JsonIgnore
//    protected Boolean deleted = Boolean.FALSE;
//
//    @ApiModelProperty(value = "版本控制", hidden = true)
//    @Version
//    @JsonIgnore
//    protected Long version;
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//
//    }
//
//    public Long getUpdateBy() {
//        return updateBy;
//    }
//
//    public void setUpdateBy(Long updateBy) {
//        this.updateBy = updateBy;
//    }
//
//    public Long getCreateBy() {
//        return createBy;
//    }
//
//    public void setCreateBy(Long createBy) {
//        this.createBy = createBy;
//    }
//
//    @JsonIgnore
//    public Boolean getDeleted() {
//        return deleted;
//    }
//
//    public void setDeleted(Boolean deleted) {
//        this.deleted = deleted;
//    }
//
//    @JsonIgnore
//    public Long getVersion() {
//        return version;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }
//}
