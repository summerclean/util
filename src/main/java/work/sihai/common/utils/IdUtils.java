package work.sihai.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 产生19位的UUID
 */
public class IdUtils {

  final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
      '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
      'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
      'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
      'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
      'Z'};

  final static Map<Character, Integer> DIGIT_MAP = new HashMap<>();

  static {
    for (int i = 0; i < DIGITS.length; i++) {
      DIGIT_MAP.put(DIGITS[i], (int) i);
    }
  }

  /**
   * 支持的最大进制数
   */
  private static final int MAX_RADIX = DIGITS.length;

  /**
   * 支持的最小进制数
   */
  private static final int MIN_RADIX = 2;

  /**
   * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
   */
  private static String toString(long i, int radix) {
    if (radix < MIN_RADIX || radix > MAX_RADIX) {
      radix = 10;
    }
    if (radix == 10) {
      return Long.toString(i);
    }

    final int size = 65;
    int charPos = 64;

    char[] buf = new char[size];
    boolean negative = (i < 0);

    if (!negative) {
      i = -i;
    }

    while (i <= -radix) {
      buf[charPos--] = DIGITS[(int) (-(i % radix))];
      i = i / radix;
    }
    buf[charPos] = DIGITS[(int) (-i)];

    if (negative) {
      buf[--charPos] = '-';
    }

    return new String(buf, charPos, (size - charPos));
  }

  private static NumberFormatException forInputString(String s) {
    return new NumberFormatException("For input string: \"" + s + "\"");
  }

  /**
   * 将字符串转换为长整型数字
   *
   * @param s 数字字符串
   * @param radix 进制数
   */
  private static long toNumber(String s, int radix) {
    if (s == null) {
      throw new NumberFormatException("null");
    }

    if (radix < MIN_RADIX) {
      throw new NumberFormatException("radix " + radix
          + " less than Numbers.MIN_RADIX");
    }
    if (radix > MAX_RADIX) {
      throw new NumberFormatException("radix " + radix
          + " greater than Numbers.MAX_RADIX");
    }

    long result = 0;
    boolean negative = false;
    int i = 0, len = s.length();
    long limit = -Long.MAX_VALUE;
    long multmin;
    Integer digit;

    if (len > 0) {
      char firstChar = s.charAt(0);
      if (firstChar < '0') {
        if (firstChar == '-') {
          negative = true;
          limit = Long.MIN_VALUE;
        } else if (firstChar != '+') {
          throw forInputString(s);
        }

        if (len == 1) {
          throw forInputString(s);
        }
        i++;
      }
      multmin = limit / radix;
      while (i < len) {
        digit = DIGIT_MAP.get(s.charAt(i++));
        if (digit == null) {
          throw forInputString(s);
        }
        if (digit < 0) {
          throw forInputString(s);
        }
        if (result < multmin) {
          throw forInputString(s);
        }
        result *= radix;
        if (result < limit + digit) {
          throw forInputString(s);
        }
        result -= digit;
      }
    } else {
      throw forInputString(s);
    }
    return negative ? result : -result;
  }

  private static String DIGITS(long val, int DIGITS) {
    long hi = 1L << (DIGITS * 4);
    return toString(hi | (val & (hi - 1)), MAX_RADIX).substring(1);
  }

  public static String generate() {
    //产生UUID
    UUID uuid = UUID.randomUUID();
    StringBuilder sb = new StringBuilder();
    //分区转换
    sb.append(DIGITS(uuid.getMostSignificantBits() >> 32, 8));
    sb.append(DIGITS(uuid.getMostSignificantBits() >> 16, 4));
    sb.append(DIGITS(uuid.getMostSignificantBits(), 4));
    sb.append(DIGITS(uuid.getLeastSignificantBits() >> 48, 4));
    sb.append(DIGITS(uuid.getLeastSignificantBits(), 12));
    return sb.toString();
  }

}
