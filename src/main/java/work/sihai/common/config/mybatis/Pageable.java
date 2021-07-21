package work.sihai.common.config.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class Pageable {
    @ApiModelProperty(value = "当前页")
    private int current = 1;
    @ApiModelProperty(value = "每页数量")
    private int size = 10;
}
