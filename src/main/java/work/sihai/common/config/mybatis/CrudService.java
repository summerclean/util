package work.sihai.common.config.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

public interface CrudService<T> extends IService<T> {


    /**
     * 根据ID判断一个实体是否存在
     * @param wrapper
     * @return
     */
    boolean exists(Wrapper<T> wrapper);

    /**
     * 根据ID判断一个实体是否存在
     */
    boolean exists(String column, Object params);


    /**
     * 根据ID判断一个实体是否存在
     */
    boolean exists(String column, List<Object> params);

    /**
     * 根据ID判断一个实体是否存在
     * @param id
     * @return
     */
    boolean exists(Serializable id);

}
