package work.sihai.common.config.mapper.serial;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> _nullArrayJsonSerializer = new ArrayJsonSerializer();
    private JsonSerializer<Object> _nullModelJsonSerializer = new ModelJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        beanProperties.forEach(writer->{
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            if (isArrayType(writer)) {
                //给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
            } else if (isModelType(writer)) {
                writer.assignNullSerializer(this.defaultNullModelJsonSerializer());
            }
        });
        return beanProperties;
    }

    // 判断是什么类型
    protected boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getClass();
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class)|| clazz.equals(Collection.class);
    }

    // 判断时是否对象
    protected boolean isModelType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getClass();
        return !isPrimitive(clazz.getName());
    }

    protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {
        return _nullArrayJsonSerializer;
    }


    protected JsonSerializer<Object> defaultNullModelJsonSerializer() {
        return _nullModelJsonSerializer;
    }

    public static boolean isPrimitive(Object className) {

        return "java.lang.Integer".equals(className) ||
                "java.lang.Byte".equals(className) ||
                "java.lang.Long".equals(className) ||
                "java.lang.Double".equals(className) ||
                "java.lang.Float".equals(className) ||
                "java.lang.Character".equals(className) ||
                "java.lang.Short".equals(className) ||
                "java.lang.String".equals(className) ||
                "java.util.Date".equals(className) ||
                "java.lang.Boolean".equals(className) ||
                "java.math.BigDecimal".equals(className);
    }

}