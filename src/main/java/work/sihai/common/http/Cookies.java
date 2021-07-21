package work.sihai.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import work.sihai.common.codec.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

public class Cookies {

    private static final Logger logger = LoggerFactory.getLogger(Cookies.class);

    private static final Random RANDOM = new Random();

    private Cookies() {

    }

    /**
     * 写cookie
     *
     * @param name     cookie 名称
     * @param value    明文值
     * @param age      过期时间 秒
     * @param path     路径
     * @param response
     */
    public static void setCookie(String name, String value, int age, String path, HttpServletResponse response) {

        try {
            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
            cookie.setMaxAge(age);
            cookie.setPath(path);
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("set cookie error {},{}", value, e);
        }
    }

    public static void setCookie(String name, String value, int age, String path, String domain, HttpServletResponse response) {

        try {
            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
            cookie.setMaxAge(age);
            cookie.setPath(path);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("set cookie error {},{}", value, e);
        }
    }

    /**
     * 通过cookie name 获得值
     *
     * @param name
     */
    public static String getCookie(String name, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(name)) {
                return null;
            }

            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie c : cookies) {
                    if (name.equals(c.getName().toString())) {
                        return URLDecoder.decode(c.getValue(), "utf-8");
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("get cookie error {},{}", name, e);
        }
        return null;
    }

    /**
     * 清除cookie值
     */
    public static String clearCookies(String domain, HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie c : cookies) {
                    c.setMaxAge(0);
                    c.setDomain(domain);
                    c.setPath("/");
                    c.setValue("");
                    response.addCookie(c);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }

    /**
     * @param path     cookie路径
     * @param domain   域
     * @param request
     * @param response
     * @notes:删除指定domain域、路径path下的所有cookies
     * @author henry 2014-5-19	下午4:01:32
     */
    public static void clearCookies(String path, String domain, HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie c : cookies) {
                    c.setMaxAge(0);
                    c.setDomain(domain);
                    c.setPath(path);
                    c.setValue("");
                    response.addCookie(c);
                }
            } else {
                return;
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    /**
     * @param request
     * @param response
     * @notes:设置全站用户唯一标识
     */
    public static String setGlobalCookieId(HttpServletRequest request, HttpServletResponse response) {
        String cookieId = getCookie("cookieId", request);
        if (StringUtils.isEmpty(cookieId)) {
            cookieId = String.valueOf(RANDOM.nextLong() * 100000);
            cookieId = MD5.getMD5Code(cookieId);
            setCookie("cookieId", cookieId, 365 * 24 * 3600, "/", "mageline.com", response);
        }
        return cookieId;
    }
}
