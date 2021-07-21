package work.sihai.common.config.jmock;

import com.github.jsonzou.jmockdata.MockConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JmockdataConfig {
    @Bean
    public MockConfig mockConfig() {
        return new MockConfig()
                // 全局配置
                .globalConfig()
                .sizeRange(1,1)
                .charSeed((char) 97, (char) 98)
                .byteRange((byte) 0, Byte.MAX_VALUE)
                .shortRange((short) 0, Short.MAX_VALUE)

                // 某些字段（名等于integerNum的字段、包含float的字段、double开头的字段）配置
                .subConfig("integerNum","*float*","double*")
                .intRange(10, 11)
                .floatRange(1.22f, 1.50f)
                .doubleRange(1.50,1.99)

                // 某个类的某些字段（long开头的字段、date结尾的字段、包含string的字段）配置。
//                .subConfig(BasicBean.class,"long*","*date","*string*")
//                .longRange(12, 13)
//                .dateRange("2018-11-20", "2018-11-30")
//                .stringSeed("SAVED", "REJECT", "APPROVED")
//                .sizeRange(1,1)

                // 全局配置
                .globalConfig()
                // 排除所有包含list/set/map字符的字段。表达式不区分大小写。
                .excludes("*List*","*Set*","*Map*")
                // 排除所有Array开头/Boxing结尾的字段。表达式不区分大小写。
//                .excludes(BasicBean.class,"*Array","Boxing*")
                ;
    }
}
