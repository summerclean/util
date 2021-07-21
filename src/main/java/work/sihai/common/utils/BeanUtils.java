package work.sihai.common.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于CGlib，扩展BeanUtils，对于复杂类型的CGlib更有优势
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {

    protected BeanUtils() {
        /* 保护 */
    }

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 实例化对象
     *
     * @param clazzStr 类名
     * @return {T}
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * copy 对象属性到另一个对象，默认不使用Convert
     *
     * @param clazz 类名
     * @return {T}
     */
    public static <T> T copy(Object src, Class<T> clazz) {
        if (src == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
        T to = newInstance(clazz);
        copier.copy(src, to, null);
        return to;
    }


    /**
     * 拷贝对象
     *
     * @param src  源对象
     * @param dist 需要赋值的对象
     */
    public static void copy(Object src, Object dist) {
        BeanCopier copier = BeanCopier.create(src.getClass(), dist.getClass(), false);
        copier.copy(src, dist, null);
    }

    public static <S, T> List<T> copyList(List<S> source, Class<T> targetClass) {
        if (source != null) {
            return source.stream().map(s -> copy(s, targetClass)).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 将对象装成map形式 注意：生成的是unmodifiableMap
     *
     * @param src 源对象
     * @return {Map<K, V>}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Object src) {
        return BeanMap.create(src);
    }

    /**
     * 复制bean属性,忽略null属性
     */
    public static void copyPropertiesIgnoreNull(Object from, Object to) {
        copyProperties(from, to);
    }

    /**
     * 复制bean，不过滤null属性
     *
     * @param from
     * @param to
     */
    public static void copyPropertiesAllowNull(Object from, Object to) {
        copyProperties(from, to, new BeanUtilsBean());
    }

    private static void copyProperties(Object from, Object to, BeanUtilsBean bub) {
        try {
            bub.copyProperties(to, from);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
