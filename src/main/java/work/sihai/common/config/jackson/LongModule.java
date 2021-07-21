package work.sihai.common.config.jackson;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author 闫四海
 * @date 2020-12-11 16:19
 */
public class LongModule extends SimpleModule {

    public LongModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(Long.class, ToStringSerializer.instance);
    }
}
