package work.sihai.common.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalUtils {

    public static boolean isBigger(BigDecimal first,BigDecimal second ){
        return first.compareTo(second) > 0;
    }

    public static boolean isBiggerOrEquals(BigDecimal first,BigDecimal second ){
        return first.compareTo(second) >= 0;
    }


    public static boolean isLess(BigDecimal first,BigDecimal second ){
        return first.compareTo(second) < 0;
    }

    public static boolean isLessOrEquals(BigDecimal first,BigDecimal second ){
        return first.compareTo(second) <= 0;
    }

    public static boolean isEquals(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }


}
