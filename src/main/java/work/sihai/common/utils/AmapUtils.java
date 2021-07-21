package work.sihai.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AmapUtils {
    public static Map<String, String> getLngAndLatFromAddress(String address){
        String str = "http://restapi.amap.com/v3/geocode/geo?address=" + address + "&output=JSON&key=703ed092fc40511fa65eb7fa77c6f1ad";
        Map<String, String> result = new HashMap();
        InputStream inputStream = null;
        try {
            URL url = new URL(str);
            HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5*1000);
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            JsonNode jsonNode = new ObjectMapper().readTree(inputStream);
            log.info("getLngAndLatFromAddress result = " + JSONObject.toJSONString(jsonNode));
            if("1".equals(jsonNode.findValue("status").textValue()) && jsonNode.findValue("geocodes").size()>0){
                String[] degree = jsonNode.findValue("geocodes").findValue("location").textValue().split(",");
                result.put("info", "success");
                result.put("lng", degree[0]);
                result.put("lat", degree[1]);
             }
        } catch (MalformedURLException e) {
            result.put("info", "erro");
            log.error("getLngAndLatFromAddress error1=" + e.getMessage(), e);
        } catch (IOException e) {
            result.put("info", "erro");
            log.error("getLngAndLatFromAddress error2=" + e.getMessage(), e);
        }finally{
            try {
                if(null != inputStream ){
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("getLngAndLatFromAddress error3=" + e.getMessage(), e);
            }
        }
        return result;
    }
}
