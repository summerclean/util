package work.sihai.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import work.sihai.common.config.rest.RestClient;
import work.sihai.common.config.jackson.JsonMapperUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author 闫四海
 * @date 2021-01-05 14:10
 */
@Component
public class RestTemplateUtil {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateUtil.class);

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestClient restClient) {
        RestTemplateUtil.restTemplate = restClient.getRestTemplate();
    }

    public static ResponseEntity<String> sendGet(String url, Map<String,String> param){
        try {
            return restTemplate.getForEntity(url, String.class,param);
        } catch (Exception e) {
            log.error("GET请求url:{}发生错误，错误原因：{}",url,e.getMessage());
            return new ResponseEntity<String>("请求错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static <T> T sendGet(String url, Map<String,String> param,Class<T> clz){
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, param);
            if (Objects.equals(responseEntity.getStatusCodeValue(),HttpStatus.OK.value())){
                return JsonMapperUtils.fromJsonString(responseEntity.getBody(),clz);
            }else {
                log.error("POST请求url:{}发生错误，错误原因：{}",url,responseEntity.getBody());
                throw new RuntimeException("系统繁忙，请稍后再试");
            }
        } catch (Exception e) {
            log.error("GET请求url:{}发生错误，错误原因：{}",url,e.getMessage());
            throw new RuntimeException("系统繁忙，请稍后再试");
        }
    }


    public static ResponseEntity<String> sendPost(String url, Object param){
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> request = new HttpEntity<String>(JsonMapperUtils.toJsonString(param), headers);
            return restTemplate.postForEntity(url,request,String.class);
        } catch (Exception e) {
            log.error("POST请求url:{}发生错误，错误原因：{}",url,e.getMessage());
            return new ResponseEntity<String>("请求错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static <T> T sendPost(String url, Object param,Class<T> clz){
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> request = new HttpEntity<String>(JsonMapperUtils.toJsonString(param), headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
            if (Objects.equals(responseEntity.getStatusCodeValue(),HttpStatus.OK.value())){
                return JsonMapperUtils.fromJsonString(responseEntity.getBody(),clz);
            }else {
                log.error("POST请求url:{}发生错误，错误原因：{}",url,responseEntity.getBody());
                throw new RuntimeException("系统繁忙，请稍后再试");
            }
        } catch (Exception e) {
            log.error("POST请求url:{}发生错误，错误原因：{}",url,e.getMessage());
            throw new RuntimeException("系统繁忙，请稍后再试");
        }

    }


}
