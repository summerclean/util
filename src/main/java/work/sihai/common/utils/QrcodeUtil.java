package work.sihai.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QrcodeUtil {

    private static final AtomicInteger INDEX = new AtomicInteger(0);

    private static final String PATH_PREFIX = "C:\\Users\\hu_19\\Documents\\";

    private static final int BLACK  = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    public static String create(String text,int wid,int height){
        Map<EncodeHintType,String> his = new HashMap<>();
        his.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
        try {
            BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,wid,height,his);
            int encodeWidth = encode.getWidth();
            int encodeHeight = encode.getHeight();
            BufferedImage image = new BufferedImage(encodeWidth, encodeHeight, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < encodeWidth; i++) {
                for (int j = 0; j < encodeHeight; j++) {
                    image.setRGB(i,j,encode.get(i,j)?BLACK:WHITE);
                }
            }
            String fileName = PATH_PREFIX + INDEX.addAndGet(1);
            ImageIO.write(image,"jpg",new File(fileName + ".jpg"));
            return fileName;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

}
