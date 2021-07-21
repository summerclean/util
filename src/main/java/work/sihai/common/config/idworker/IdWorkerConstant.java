package work.sihai.common.config.idworker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName IdWorkerConstant
 * @Author Ali
 * @Date 2020/9/18 13:40
 */
@Component
public class IdWorkerConstant {

    @Value("${com.mageline.vip.workerId:1}")
    private long workerId;
    @Value("${com.mageline.vip.datacenterId:1}")
    private long datacenterId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }
}
