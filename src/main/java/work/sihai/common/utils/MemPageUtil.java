package work.sihai.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author 闫四海
 * @date 2021-01-18 15:12
 */
public class MemPageUtil {

    /**
     * @desc: 内存分页工具
     * @param current
     * @param size
     * @param result
     * @author: 闫四海
     * @date: 2021/1/18
     * @return:
     **/
    public static  <T> IPage<T> getPageResult(Integer current, Integer size, List<T> result){
        IPage<T> pageResult = new Page<T>(current,size);
        int first = (current - 1) * size;
        int last = Math.min(((first + size)), result.size());
        pageResult.setPages((result.size() + size - 1) / size);
        pageResult.setTotal(result.size());
        if (first >= result.size() || last > result.size()){
            return pageResult;
        }
        pageResult.setRecords(result.subList(first,last));
        return pageResult;
    }
}
