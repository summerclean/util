package work.sihai.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import work.sihai.common.codec.MD5;
import work.sihai.common.config.jackson.JsonMapperUtils;
import work.sihai.common.config.properties.TencentGeoProperties;
import work.sihai.common.http.RestTemplateUtil;

import java.util.*;


/**
 * @author 闫四海
 * @date 2021-01-05 14:27
 */
//@Slf4j
@Component
public class TencentGeoUtil {

    private static final Logger log = LoggerFactory.getLogger(TencentGeoUtil.class);

    private static TencentGeoProperties geoProperties;

    @Autowired
    public void setGeoProperties(TencentGeoProperties geoProperties) {
        TencentGeoUtil.geoProperties = geoProperties;
    }

    public static List<Map<String,Object>> district(Integer id){
        String url = geoProperties.getBaseUrl() + geoProperties.getDistrictUrl() + "?key={key}&sig={sig}";
        Map<String,String> param = Maps.newHashMap();
        param.put("key",geoProperties.getKey());
        if (Objects.nonNull(id)){
            url = url + "&id={id}";
            param.put("id",id.toString());
        }
        String sign = sign(geoProperties.getDistrictUrl(), param, geoProperties.getSecret());
        param.put("sig",sign);
        ResponseEntity<String> responseEntity = RestTemplateUtil.sendGet(url,param);
        log.info("请求腾讯地图服务行政区划接口，请求url:{},参数：{}，response：{}", url, JsonMapperUtils.toJsonString(param),JsonMapperUtils.toJsonString(responseEntity));
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value() || responseEntity.getBody()==null){
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        DistrictResult districtResult = JsonMapperUtils.fromJsonString(responseEntity.getBody(), DistrictResult.class);
        List<Map<String,Object>> result = Lists.newArrayList();
        if (Objects.isNull(districtResult) || ArrayUtils.isEmpty(districtResult.getResult()) || ArrayUtils.isEmpty(districtResult.getResult()[0])){
            return result;
        }
        District[] districts = districtResult.getResult()[0];
        for (District district : districts) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("districtId",district.getId());
            temp.put("name",district.getName());
            temp.put("districtName",district.getFullname());
            result.add(temp);
        }
        return result;
    }



    public static Map<String,Object> myCity(double lng, double lat){
        Map<String,String> param = Maps.newHashMap();
        param.put("location",lat + "," + lng);
        param.put("key",geoProperties.getKey());
        String sign = sign(geoProperties.getLocationUrl(), param, geoProperties.getSecret());
        param.put("sig",sign);
        String url = geoProperties.getBaseUrl() +
                geoProperties.getLocationUrl() +
                "?" + "key={key}&location={location}&sig={sig}";
        ResponseEntity<String> responseEntity = RestTemplateUtil.sendGet(url,param);
        log.info("请求腾讯地图服务定位接口,请求url:{},参数：{}，response：{}", url, JsonMapperUtils.toJsonString(param),JsonMapperUtils.toJsonString(responseEntity));
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value() || responseEntity.getBody()==null){
            throw new RuntimeException("暂时无法获取地图信息，请稍后重试");
        }
        LocationResult locationResult = JsonMapperUtils.fromJsonString(responseEntity.getBody(), LocationResult.class);
        if (Objects.isNull(locationResult) || Objects.isNull(locationResult.getResult())
                || Objects.isNull(locationResult.getResult().getAd_info())
                || Objects.isNull(locationResult.getResult().getAd_info().getNation_code())
                || Objects.isNull(locationResult.getResult().getAd_info().getCity_code())){
            throw new RuntimeException("暂时无法获取地图信息，请稍后重试");
        }
        String nationCode = locationResult.getResult().getAd_info().getNation_code();
        String cityCode = locationResult.getResult().getAd_info().getCity_code();
        Map<String,Object> cityDto = new HashMap<>();
        cityDto.put("cityCode",Integer.parseInt(cityCode.substring(nationCode.length())));
        cityDto.put("cityName",locationResult.getResult().getAd_info().getCity());
        cityDto.put("city",locationResult.getResult().getAd_info().getCity());
        return cityDto;
    }

    public static Map<String,Object> analyzeAddress(String address) {
        Map<String,String> param = Maps.newHashMap();
        param.put("key",geoProperties.getKey());
        param.put("address",address);
        String sign = sign(geoProperties.getAnalyzeUrl(), param, geoProperties.getSecret());
        param.put("sig",sign);
        String url = geoProperties.getBaseUrl() + geoProperties.getAnalyzeUrl() +
                "?key={key}&address={address}&sig={sig}";
        ResponseEntity<String> responseEntity = RestTemplateUtil.sendGet(url,param);
        log.info("请求腾讯地图服务行政区划接口，请求url:{},参数：{}，response：{}", url, JsonMapperUtils.toJsonString(param),JsonMapperUtils.toJsonString(responseEntity));
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value() || responseEntity.getBody()==null){
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        AnalyzeResult analyzeResult = JsonMapperUtils.fromJsonString(responseEntity.getBody(), AnalyzeResult.class);
        if (Objects.isNull(analyzeResult) || Objects.isNull(analyzeResult.getResult())
                || Objects.isNull(analyzeResult.getResult().getLocation())
                || Objects.isNull(analyzeResult.getResult().getLocation().getLat())
                || Objects.isNull(analyzeResult.getResult().getLocation().getLng())){
            throw new RuntimeException("暂时无法获取地图信息，请稍后重试");
        }
        Map<String,Object> geoInfoDto = new HashMap<>();
        geoInfoDto.put("lat",analyzeResult.getResult().getLocation().getLat());
        geoInfoDto.put("lng",analyzeResult.getResult().getLocation().getLng());
        return geoInfoDto;
    }

    private static String sign(String url,Map<String, String> paramValues,String secret) {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        Collections.sort(paramNames);
        sb.append(url).append("?");
        Iterator<String> iterator = paramNames.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            sb.append(key).append("=").append(paramValues.get(key));
            if (iterator.hasNext()){
                sb.append("&");
            }
        }
        sb.append(secret);

        return MD5.getMD5Code(sb.toString());
    }

    private static class AnalyzeResult{
        private Integer status;
        private String message;
        private AnalyzeAddress result;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public AnalyzeAddress getResult() {
            return result;
        }

        public void setResult(AnalyzeAddress result) {
            this.result = result;
        }
    }

    private static class AnalyzeAddress{
        private String title;
        private Location location;
        private AddressInfo ad_info;
        private AddressComponents address_components;
        private Double similarity;
        private Double deviation;
        private Double reliability;
        private Double level;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public AddressInfo getAd_info() {
            return ad_info;
        }

        public void setAd_info(AddressInfo ad_info) {
            this.ad_info = ad_info;
        }

        public AddressComponents getAddress_components() {
            return address_components;
        }

        public void setAddress_components(AddressComponents address_components) {
            this.address_components = address_components;
        }

        public Double getSimilarity() {
            return similarity;
        }

        public void setSimilarity(Double similarity) {
            this.similarity = similarity;
        }

        public Double getDeviation() {
            return deviation;
        }

        public void setDeviation(Double deviation) {
            this.deviation = deviation;
        }

        public Double getReliability() {
            return reliability;
        }

        public void setReliability(Double reliability) {
            this.reliability = reliability;
        }

        public Double getLevel() {
            return level;
        }

        public void setLevel(Double level) {
            this.level = level;
        }
    }




    private static class LocationResult{
        private Integer status;
        private String message;
        private String data_version;
        private MyLocation result;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData_version() {
            return data_version;
        }

        public void setData_version(String data_version) {
            this.data_version = data_version;
        }

        public MyLocation getResult() {
            return result;
        }

        public void setResult(MyLocation result) {
            this.result = result;
        }
    }

    private static class MyLocation{
        private Location location;
        private String address;
        private AddressInfo ad_info;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public AddressInfo getAd_info() {
            return ad_info;
        }

        public void setAd_info(AddressInfo ad_info) {
            this.ad_info = ad_info;
        }
    }

    private static class AddressInfo {
        private String nation_code;
        private String adcode;
        private String city_code;
        private String name;
        private Location location;
        private String street_number;
        private String nation;
        private String province;
        private String city;
        private String district;

        public String getNation_code() {
            return nation_code;
        }

        public void setNation_code(String nation_code) {
            this.nation_code = nation_code;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getStreet_number() {
            return street_number;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

    private static class AddressComponents {
        private String province;
        private String city;
        private String district;
        private String street;
        private String street_number;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreet_number() {
            return street_number;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }
    }




    private static class DistrictResult {
        private Integer status;
        private String message;
        private String data_version;
        private District[][] result;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData_version() {
            return data_version;
        }

        public void setData_version(String data_version) {
            this.data_version = data_version;
        }

        public District[][] getResult() {
            return result;
        }

        public void setResult(District[][] result) {
            this.result = result;
        }
    }



    private static class Location{
        private Double lat;
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }

    private static class District{
        private Integer id;
        private String name;
        private String fullname;
        private String[] pinyin;
        private Location location;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String[] getPinyin() {
            return pinyin;
        }

        public void setPinyin(String[] pinyin) {
            this.pinyin = pinyin;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }


}
