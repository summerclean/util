package work.sihai.common.config.mybatis;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseEntity <T extends Model> extends Model<T> {

    @ApiModelProperty(value = "主键ID")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.getId();
    }
}
