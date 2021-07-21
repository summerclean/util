package work.sihai.common.utils;

/**
 * @author 闫四海
 * @date 2021-01-27 17:38
 */
public class GeoDistanceUtils {

    /**
     * 默认地球半径
     */
    private static final double EARTH_RADIUS = 6371000;//赤道半径(单位m)

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lng1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lng2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        long l = Math.round(s * 10_000) / 10000;
        return l;
    }
}
