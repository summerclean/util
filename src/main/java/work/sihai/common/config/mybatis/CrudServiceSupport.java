package work.sihai.common.config.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

public class CrudServiceSupport<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CrudService<T> {

    /**
     * 判断是否存在Entity
     */
    @Override
    public boolean exists(Wrapper<T> wrapper) {
        return count(wrapper) > 0;
    }

    /**
     * 判断是否存在Entity
     */
    @Override
    public boolean exists(String column, Object params) {
        Wrapper wrapper = new QueryWrapper<T>().eq(column, params);
        return exists(wrapper);
    }

    @Override
    public boolean exists(String column, List<Object> params) {
        Wrapper<T> wrapper = new QueryWrapper<T>().in(column, params);
        return exists(wrapper);
    }

    /**
     * 判断是否存在Entity
     */
    @Override
    public boolean exists(Serializable id) {
        return getById(id) != null;
    }

}
