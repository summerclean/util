package work.sihai.common.config.jackson;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 闫四海
 * @date 2020-12-03 13:59
 */
public class LocalDateTimeModule extends SimpleModule {

    public LocalDateTimeModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(TimePatternConstant.YEAR_TIME_PATTERN));
        this.addSerializer(LocalDate.class,new LocalDateSerializer(TimePatternConstant.YEAR_PATTERN));
        this.addSerializer(LocalTime.class,new LocalTimeSerializer(TimePatternConstant.TIME_PATTERN));
        this.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(TimePatternConstant.YEAR_TIME_PATTERN));
        this.addDeserializer(LocalDate.class,new LocalDateDeserializer(TimePatternConstant.YEAR_PATTERN));
        this.addDeserializer(LocalTime.class,new LocalTimeDeserializer(TimePatternConstant.TIME_PATTERN));
    }
}
