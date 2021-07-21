package work.sihai.common.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author 闫四海
 * @date 2020-08-29 13:23
 */
@Component
public class JsonMapperUtils {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper){
        JsonMapperUtils.objectMapper = objectMapper;
    }

    public static ObjectMapper getInstance(){
        return objectMapper;
    }

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String toJsonString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("系统转换异常");
        }
    }

    /**
     * 字符串转换为自定义对象
     * @param str 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJsonString(String str, Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            throw new RuntimeException("系统转换异常");
        }
    }

    /**
     * 获取泛型的 Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * @desc: 转为集合
     * @param str
     * @param collectionClazz
     * @param elementClazzes
     * @author: 闫四海
     * @date: 2020/9/11
     * @return:
     **/
    public static <T> T parseCollection(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        try {
            return objectMapper.readValue(str, getCollectionType(collectionClazz,elementClazzes));
        } catch (IOException e) {
            throw new RuntimeException("系统转换异常");
        }
    }


    /**
     * @desc: 集合转为复杂对象
     * @param json
     * @author: 闫四海
     * @date: 2020/9/11
     * @return:
     **/
    public static <T> T parseObject(String json,TypeReference<T> typeReference) {
        try {
            return  objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("系统转换异常");
        }
    }


}
