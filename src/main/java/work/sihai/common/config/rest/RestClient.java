package work.sihai.common.config.rest;

import org.springframework.web.client.RestTemplate;

public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
