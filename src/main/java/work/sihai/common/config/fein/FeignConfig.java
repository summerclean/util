//package work.sihai.common.config.fein;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import feign.*;
//import feign.codec.Decoder;
//import feign.codec.Encoder;
//import feign.optionals.OptionalDecoder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
//import org.springframework.cloud.openfeign.support.SpringDecoder;
//import org.springframework.cloud.openfeign.support.SpringEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.RequestContextListener;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Enumeration;
//
//@Configuration
//public class FeignConfig {
//    private static final Logger log = LoggerFactory.getLogger(FeignConfig.class);
//    private static final String AGENT_ERROR_CODE = "code";
//    private static final String TRADE_ERROR_CODE = "status";
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
//    public FeignConfig() {
//    }
//
//    @Bean
//    public Encoder feignEncoder() {
//        return new SpringEncoder(this.messageConverters);
//    }
//
//    @Bean
//    public Decoder feignDecoder() {
//        ResponseEntityDecoder responseDecoder = new ResponseEntityDecoder(new SpringDecoder(this.messageConverters));
//        return new OptionalDecoder(new FeignConfig.ExceptionDecoder(responseDecoder));
//    }
//
//    @Bean
//    public FeignConfig.FeignHeaderRequestInterceptor feignHeaderRequestInterceptor() {
//        log.debug("请求头透传===============FeignHeaderRequestInterceptor===============================");
//        return new FeignConfig.FeignHeaderRequestInterceptor();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({RequestContextListener.class})
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
//
//    public class FeignHeaderRequestInterceptor implements RequestInterceptor {
//        public FeignHeaderRequestInterceptor() {
//        }
//
//        public void apply(RequestTemplate template) {
//            FeignConfig.log.debug("请求头透传==============================================");
//            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//            if (requestAttributes == null) {
//                FeignConfig.log.debug("RequestAttributes is null============================");
//                if (FeignConfig.log.isDebugEnabled()) {
//                    FeignConfig.log.debug("RequestAttributes is null============================");
//                }
//
//            } else {
//                HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//                Enumeration<String> headerNames = request.getHeaderNames();
//                if (headerNames != null) {
//                    if (FeignConfig.log.isDebugEnabled()) {
//                        FeignConfig.log.debug("请求头透传============================");
//                    }
//
//                    while(true) {
//                        String name;
//                        do {
//                            if (!headerNames.hasMoreElements()) {
//                                return;
//                            }
//
//                            name = (String)headerNames.nextElement();
//                        } while(!"Cookie".equalsIgnoreCase(name) && !"Authorization".equalsIgnoreCase(name));
//
//                        Enumeration<String> values = request.getHeaders(name);
//                        ArrayList valueList = new ArrayList();
//
//                        while(values.hasMoreElements()) {
//                            valueList.add(values.nextElement());
//                        }
//
//                        template.header(name, valueList);
//                        FeignConfig.log.debug("请求头透传==========header:" + name + "==================");
//                        if (FeignConfig.log.isDebugEnabled()) {
//                            FeignConfig.log.debug("请求头透传==========header:" + name + "==================");
//                        }
//                    }
//                } else {
//                    FeignConfig.log.debug("请求头透传 is null============================");
//                }
//            }
//        }
//    }
//
//    private final class ExceptionDecoder implements Decoder {
//        private final Decoder decoder;
//
//        public ExceptionDecoder(Decoder decoder) {
//            this.decoder = decoder;
//        }
//
//        public Object decode(Response response, Type type) throws IOException, FeignException {
//            Response.Body body = response.body();
//            if (body != null) {
//                try {
//                    String bodyStr = Util.toString(body.asReader());
//                    JSONObject responseJson = JSON.parseObject(bodyStr);
//                    Integer status = 0;
//                    String errorMsg = "";
//                    if (responseJson.containsKey("code")) {
//                        status = responseJson.getInteger("code");
//                        if (status != 1) {
//                            errorMsg = responseJson.getString("msg");
//                        }
//                    } else if (responseJson.containsKey("status")) {
//                        status = responseJson.getInteger("status");
//                        if (status != 1) {
//                            errorMsg = responseJson.getString("detail");
//                        }
//                    }
//
//                    if (status != 1) {
//                        throw new RuntimeException(errorMsg);
//                    }
//                } catch (Exception var8) {
//                    //
//                }
//            }
//
//            return this.decoder.decode(response, type);
//        }
//    }
//}
