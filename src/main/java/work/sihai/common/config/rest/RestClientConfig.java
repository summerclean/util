package work.sihai.common.config.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Value("${rest.config.connectTimeout:5000}")
    private int connectTimeout;

    @Value("${rest.config.readTimeout:5000}")
    private int readTimeout;

    @Bean
    public RestClient restClient() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connectTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return new RestClient(restTemplate);
    }

}
