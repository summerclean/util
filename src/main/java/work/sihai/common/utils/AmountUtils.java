package work.sihai.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtils {
    private static final String[] CN_UPPER_NUMBER = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_UPPER_MONETRAY_UNIT = new String[]{"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};
    private static final String CN_FULL = "整";
    private static final String CN_NEGATIVE = "负";
    private static final int MONEY_PRECISION = 2;
    private static final String CN_ZEOR_FULL = "零元整";

    public AmountUtils() {
    }

    public static String toCapitalization(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        int signum = numberOfMoney.signum();
        if (signum == 0) {
            return "零元整";
        } else {
            long number = numberOfMoney.movePointRight(2).setScale(0, 4).abs().longValue();
            long scale = number % 100L;
            int numIndex = 0;
            boolean getZero = false;
            if (scale <= 0L) {
                numIndex = 2;
                number /= 100L;
                getZero = true;
            }

            if (scale > 0L && scale % 10L <= 0L) {
                numIndex = 1;
                number /= 10L;
                getZero = true;
            }

            for(int zeroSize = 0; number > 0L; ++numIndex) {
                int numUnit = (int)(number % 10L);
                if (numUnit > 0) {
                    if (numIndex == 9 && zeroSize >= 3) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                    }

                    if (numIndex == 13 && zeroSize >= 3) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                    }

                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                    getZero = false;
                    zeroSize = 0;
                } else {
                    ++zeroSize;
                    if (!getZero) {
                        sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                    }

                    if (numIndex == 2) {
                        if (number > 0L) {
                            sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                        }
                    } else if ((numIndex - 2) % 4 == 0 && number % 1000L > 0L) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }

                    getZero = true;
                }

                number /= 10L;
            }

            if (signum == -1) {
                sb.insert(0, "负");
            }

            return sb.toString();
        }
    }

    public static BigDecimal fToY(Long amount) {
        long l = amount == null ? 0L : amount;
        return BigDecimal.valueOf(l).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
    }

    public static Long yToF(BigDecimal amount) {
        BigDecimal decimal = amount == null ? BigDecimal.ZERO : amount;
        return decimal.multiply(BigDecimal.valueOf(100L)).longValue();
    }
}
