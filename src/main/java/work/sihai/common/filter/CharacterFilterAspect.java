package work.sihai.common.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import work.sihai.common.filter.annotation.CharacterFilterField;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author max_sun
 * @date 2020/9/10 11:50
 */
@Aspect
@Component
public class CharacterFilterAspect {
    private static final Logger logger = LoggerFactory.getLogger(CharacterFilterAspect.class);

    @Pointcut("@annotation(work.sihai.common.filter.annotation.CharacterFilter)")
    public void characterFilterPointcut() {

    }

    @Around("characterFilterPointcut()")
    public Object characterFilterAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        try {
            if (args != null) {
                int i = 0;
                for (Object arg : args) {
                    if (arg.getClass().isAssignableFrom(String.class)) {
                        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
                        if (parameterAnnotations != null && parameterAnnotations.length > i) {
                            Annotation[] parameterAnnotation = parameterAnnotations[i];
                            if (parameterAnnotation != null) {
                                for (Annotation annotation : parameterAnnotation) {
                                    if (annotation instanceof CharacterFilterField) {
                                        args[i] = ((String) arg).replaceAll(((CharacterFilterField) annotation).regex(), "");
                                    }
                                }
                            }
                        }
                    } else {
                        filter(arg);
//                        Field[] fields = getAllFields(arg.getClass());
//                        for (Field field : fields) {
//                            CharacterFilterField characterFilterField = field.getAnnotation(CharacterFilterField.class);
//                            if (characterFilterField != null && field.getType().isAssignableFrom(String.class)) {
//                                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(arg.getClass(), field.getName());
//                                if (propertyDescriptor != null) {
//                                    String value = (String) propertyDescriptor.getReadMethod().invoke(arg);
//                                    propertyDescriptor.getWriteMethod().invoke(arg, value.replaceAll(characterFilterField.regex(), ""));
//                                }
//                            }
//                        }
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            logger.warn("过滤特殊字符失败");
        }
        return proceedingJoinPoint.proceed(args);
    }

    private void filter(Object arg) throws InvocationTargetException, IllegalAccessException {
        if (arg == null) {
            return;
        }
        Field[] fields = getAllFields(arg.getClass());
        for (Field field : fields) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(arg.getClass(), field.getName());
            CharacterFilterField characterFilterField = field.getAnnotation(CharacterFilterField.class);
            if (characterFilterField != null && field.getType().isAssignableFrom(String.class)) {
                if (propertyDescriptor != null) {
                    String value = (String) propertyDescriptor.getReadMethod().invoke(arg);
                    propertyDescriptor.getWriteMethod().invoke(arg, value.replaceAll(characterFilterField.regex(), ""));
                }
            } else if (characterFilterField != null) {
                if (propertyDescriptor != null) {
                    filter(propertyDescriptor.getReadMethod().invoke(arg));
                }
            }
        }
    }

    public Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

}
