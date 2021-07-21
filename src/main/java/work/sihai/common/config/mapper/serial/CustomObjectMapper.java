package work.sihai.common.config.mapper.serial;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import work.sihai.common.config.mybatis.BaseResponse;

import java.io.IOException;

public class CustomObjectMapper extends ObjectMapper {

    /**
     * 自定义解析
     */
    private static final long serialVersionUID = 1L;

    public CustomObjectMapper() {
        super();
        /***处理集合和对象**/
        this.setSerializerFactory(this.getSerializerFactory().withSerializerModifier(new SerializerModifier()));
        /***处理字段**/
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
                    throws IOException {
                if (jg.getCurrentValue() instanceof BaseResponse) {
                    jg.writeObject(new JSONObject());
                } else {
                    jg.writeString("");
                }
            }
        });
    }
}



