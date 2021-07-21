package work.sihai.common.config.idworker;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName OrderOperationConfig
 * @Author Ali
 * @Date 2020/8/4 14:54
 */
@Configuration
public class IdWorkerConfig {

    @Resource
    private IdWorkerConstant idWorkerConstant;


    @PostConstruct
    public void autoScanMenu() {
        IdWorker.initSequence(idWorkerConstant.getWorkerId(), idWorkerConstant.getDatacenterId());
    }

}
