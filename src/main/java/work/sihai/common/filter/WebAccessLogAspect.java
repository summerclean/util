package work.sihai.common.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import work.sihai.common.config.jackson.JsonMapperUtils;
import work.sihai.common.filter.annotation.IgnoreLog;
import work.sihai.common.http.HttpWebUtils;
import work.sihai.common.http.RequestHelper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Order(3)
@Component
public class WebAccessLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebAccessLogAspect.class);

    @Pointcut("execution(public * *(..)))")
    public void webLog() {
    }
    //&& !@annotation(ignoreLog)
    @Before("webLog() ")
    public void doBefore(JoinPoint joinPoint) {
        StartTimeHolder.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        HttpServletRequest request = HttpWebUtils.getRequest();

        if (null != request) {
            String bodyJsonStr = null;
            if (request instanceof BodyReaderHttpServletRequestWrapper) {
                bodyJsonStr = RequestHelper.getBodyString(request);
            }
            // 记录下请求内容
            String url = request.getRequestURL().toString();
            String requestMethod = request.getMethod();
            String ip = HttpWebUtils.getClientIp();
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            int argLen = joinPoint.getArgs().length;
            String params = "";
            if (argLen > 0) {
                List<Object> notOutArgs = Stream.of(joinPoint.getArgs())
                        .filter(x -> !(x instanceof ServletResponse)).collect(Collectors.toList());
                if (notOutArgs.size() > 0) {
                    params = JsonMapperUtils.toJsonString(notOutArgs);
                }
            }
            try {
                logger.info("Request URL:[{} {}],Client IP:[{}],Method:[{}.{}],Params:[{}],body:[{}]",
                        url, requestMethod, ip, className, methodName, params, bodyJsonStr);
            } catch (Exception e) {
                logger.info("Request Params is unvisible");
            }
        }
    }

    @Before("webLog() && @annotation(ignoreLog)")
    public void doBeforeWithLog(JoinPoint joinPoint, IgnoreLog ignoreLog) {
        StartTimeHolder.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        HttpServletRequest request = HttpWebUtils.getRequest();
        if (null != request) {
            // 记录下请求内容
            logger.info("Request URL    : [{} {}] ", request.getMethod(), request.getRequestURL().toString());
            logger.info("Client IP      : [{}] ", HttpWebUtils.getClientIp());
            logger.info("Request Method : [{}.{}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            try {
                if (!ignoreLog.ignoreRequest()) {
                    logger.info("Request Params : [{}] ", JsonMapperUtils.toJsonString(joinPoint.getArgs()));
                }
            } catch (Exception e) {
                logger.info("Request Params is unvisible");
            }
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        try {
            logger.info("Response Result : [{}] ", JsonMapperUtils.toJsonString(ret));
        } catch (Exception e) {
            logger.info("Response Result is unvisible");
        }
        logger.info("Total Spend Time : {} ms", (System.currentTimeMillis() - StartTimeHolder.get()));
        StartTimeHolder.remove();
    }

}

