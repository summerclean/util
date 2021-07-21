package work.sihai.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import work.sihai.common.http.HttpWebUtils;

import java.io.Serializable;
import java.util.Date;

public class BaseMetaObjectHandler implements MetaObjectHandler {
    public static final String CREATE_BY = "createBy";
    public static final String CREATE_DATE = "createDate";
    public static final String UPDATE_BY = "updateBy";
    public static final String UPDATE_DATE = "updateDate";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter(CREATE_BY) && null == getFieldValByName(CREATE_BY, metaObject) && metaObject.hasSetter(CREATE_BY)) {
            Serializable currentUserId = null;
            if (metaObject.hasSetter(CREATE_BY) && null != currentUserId) {
                this.setFieldValByName(CREATE_BY, currentUserId, metaObject);
            }
        }

        if (metaObject.hasGetter(CREATE_DATE) &&
                null == getFieldValByName(CREATE_DATE, metaObject)  && metaObject.hasSetter(CREATE_DATE)) {
            this.setFieldValByName(CREATE_DATE, new Date(), metaObject);
        }

        if (metaObject.hasGetter(UPDATE_BY) && null == metaObject.getValue(UPDATE_BY) && metaObject.hasSetter(UPDATE_BY)) {
            Serializable currentUserId = null;
            if (metaObject.hasSetter(UPDATE_BY) && currentUserId != null) {
                this.setFieldValByName(UPDATE_BY, currentUserId, metaObject);
            }
        }

        if (metaObject.hasGetter(UPDATE_DATE) && null == metaObject.getValue(UPDATE_DATE) && metaObject.hasSetter(UPDATE_DATE)) {
            this.setFieldValByName(UPDATE_DATE, new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        //获取当前登录用户
        Serializable currentUserId = HttpWebUtils.getCurrentUserId();
        if (metaObject.hasSetter(UPDATE_BY)) {
            if (currentUserId != null) {
                this.setFieldValByName(UPDATE_BY, currentUserId, metaObject);
            }
        }
        if (metaObject.hasSetter(UPDATE_DATE)) {
            this.setFieldValByName(UPDATE_DATE, new Date(), metaObject);
        }

    }

}


