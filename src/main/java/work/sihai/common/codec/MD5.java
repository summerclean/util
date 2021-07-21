package work.sihai.common.codec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static final String[] STR_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private MD5() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return STR_DIGITS[iD1] + STR_DIGITS[iD2];
    }

    private static String byteToString(byte[] bByte) {
        StringBuilder buffer = new StringBuilder(0);

        for(int i = 0; i < bByte.length; ++i) {
            buffer.append(byteToArrayString(bByte[i]));
        }

        return buffer.toString();
    }

    public static String getMD5Code(String strObj) {
        String resultString = null;

        try {
            new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }

    public static String string2MD5(String inStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = inStr.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for(int i = 0; i < charArray.length; ++i) {
                byteArray[i] = (byte)charArray[i];
            }

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuilder hexValue = new StringBuilder(0);

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            return hexValue.toString();
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }
    }

}
