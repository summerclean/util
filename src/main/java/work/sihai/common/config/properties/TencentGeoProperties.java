package work.sihai.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 闫四海
 * @date 2021-01-05 14:30
 */
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "map.tencent.geo")
@Component
public class TencentGeoProperties {

    private String key;

    private String secret;

    private String baseUrl;

    private String locationUrl;

    private String districtUrl;

    private String analyzeUrl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getDistrictUrl() {
        return districtUrl;
    }

    public void setDistrictUrl(String districtUrl) {
        this.districtUrl = districtUrl;
    }

    public String getAnalyzeUrl() {
        return analyzeUrl;
    }

    public void setAnalyzeUrl(String analyzeUrl) {
        this.analyzeUrl = analyzeUrl;
    }
}
