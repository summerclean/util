package work.sihai.common.config.mapper.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ModelJsonSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (value == null) {
            jgen.writeStartObject();
            jgen.writeEndObject();
        } else {
            jgen.writeObject(value);
        }
    }
}