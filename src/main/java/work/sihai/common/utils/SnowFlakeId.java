package work.sihai.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class SnowFlakeId {

    private static final Logger log = LoggerFactory.getLogger(SnowFlakeId.class);

    public static Long mac;
    public static Long ip;


    /**
     * 开始时间截 (2021-01-01)
     */
    private final static long twepoch = 1609430400000L;

    /**
     * 机器id所占的位数
     */
    private final static long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final static long datacenterIdBits = 5L;

    /**
     * 序列在id中占的位数
     */
    private final static long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final static long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final static long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final static long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private static long lastTimestamp = -1L;

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private static synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        if (mac == null) {
            mac = getMac();
        }
        if (ip == null) {
            ip = getIp();
        }
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (mac << datacenterIdShift)
                | (ip << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected static long timeGen() {
        return System.currentTimeMillis();
    }


    public static Long getMac() {
        InetAddress adress = null;
        try {
            adress = InetAddress.getLocalHost();
            NetworkInterface net = NetworkInterface.getByInetAddress(adress);
            byte[] macBytes = net.getHardwareAddress();
            int sum = 0;
            for (int b : macBytes) {
                sum += Math.abs(b);
            }
            return (long) (sum % 32);
        } catch (Exception e) {
            e.printStackTrace();
            return ThreadLocalRandom.current().nextLong(0,31);
        }

    }

    public static Long getIp() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            // 如果获取失败，则使用随机数备用
            return ThreadLocalRandom.current().nextLong(0,31);
        }

    }

    public static String getNextId() {
        return nextId() + String.format("%02d", System.nanoTime() % 100);
    }

    public static int[] toCodePoints(CharSequence str) {
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            return new int[0];
        } else {
            String s = str.toString();
            int[] result = new int[s.codePointCount(0, s.length())];
            int index = 0;
            for(int i = 0; i < result.length; ++i) {
                result[i] = s.codePointAt(index);
                index += Character.charCount(result[i]);
            }
            return result;
        }
    }


}
