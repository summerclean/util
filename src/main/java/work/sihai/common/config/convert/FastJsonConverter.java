package work.sihai.common.config.convert;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class FastJsonConverter extends WebMvcConfigurationSupport {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                // 防止循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                // Enum输出name()或者original,默认为false
                SerializerFeature.WriteEnumUsingToString,
                // Date使用ISO8601格式输出，默认为false
                SerializerFeature.UseISO8601DateFormat,
                // List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                // 字符类型字段如果为null,输出为”“,而非null
                SerializerFeature.WriteNullStringAsEmpty,
                // 数值字段如果为null,输出为0,而非null
                SerializerFeature.WriteNullNumberAsZero,
                // Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                // 输出值为null的字段
                SerializerFeature.WriteMapNullValue
        );
        ValueFilter otherFilter = (obj, s, v) -> {
            if (v == null) {
                return "";
            }
            return v;
        };
        fastJsonConfig.setSerializeFilters(otherFilter);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastJsonHttpMessageConverter);
    }
}