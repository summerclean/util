package work.sihai.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情处理类
 */
public class EmojiUtilFY {
    /**
     * 将str中的emoji表情转为byte数组
     *
     * @param str
     * @return
     */
    public static String resolveToByteFromEmoji(String str) {
        if(str==null){
            return null;
        }
        StringBuffer sb2 = new StringBuffer();

        try{
            str = new String(str.getBytes("UTF-8"), "UTF-8");

            Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                matcher.appendReplacement(sb2, resolveToByte(matcher.group(0)));
            }
            matcher.appendTail(sb2);
        }catch (Exception e) {
        }
        return sb2.toString();
    }

    /**
     * 将str中的byte数组类型的emoji表情转为正常显示的emoji表情
     *
     * @return
     */
    public static String resolveToEmojiFromByte(Object obj) {
        if(null == obj){
            return null;
        }

        String str = obj.toString();
        Pattern pattern2 = Pattern.compile("<:([[-]\\d*[,]]+):>");
        Matcher matcher2 = pattern2.matcher(str);
        StringBuffer sb3 = new StringBuffer();
        while (matcher2.find()) {
            matcher2.appendReplacement(sb3, resolveToEmoji(matcher2.group(0)));
        }
        matcher2.appendTail(sb3);
        String	sb = sb3.toString();


        return sb;
    }

    private static String resolveToByte(String str) {
        byte[] b;
        StringBuffer sb = new StringBuffer();
        try {
            b = str.getBytes("utf-8");
            sb.append("<:");
            for (int i = 0; i < b.length; i++) {
                if (i < b.length - 1) {
                    sb.append(Byte.valueOf(b[i]).toString() + ",");
                } else {
                    sb.append(Byte.valueOf(b[i]).toString());
                }
            }
            sb.append(":>");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return sb.toString();
    }

    private static String resolveToEmoji(String str) {
        str = str.replaceAll("<:", "").replaceAll(":>", "");
        String[] s = str.split(",");
        byte[] b = new byte[s.length];
        for (int i = 0; i < s.length; i++) {
            b[i] = Byte.valueOf(s[i]);
        }
        String sb = "";
        try {
            sb = new String(b,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb;
    }

}
