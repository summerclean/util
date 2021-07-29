//package work.sihai.common.config.mapper;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.text.SimpleDateFormat;
//
////@Configuration
//public class CustomObjectMapperConfig {
//    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//
//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
//        CustomObjectMapper mapper = new CustomObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
//        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter() {
//            @Override
//            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//                // 使用 Jackson 的 ObjectMapper 将 Java 对象转换成 Json String
//                String json = mapper.writeValueAsString(object);
//                outputMessage.getBody().write(json.getBytes());
//            }
//        };
//        converter.setObjectMapper(mapper);
//        return converter;
//    }
//}
