package work.sihai.common.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DecodeUtils {

    /**
     *  解码
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static JSONObject decode(String data, String key) throws Exception {
        // 手机端参数解码
        String resJson = DESUtil.decryptDES(data, key);
        //String resJson = data;
        JSONObject obj= JSON.parseObject(resJson);
        return obj;
    }
}
