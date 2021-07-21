package work.sihai.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import work.sihai.common.config.constant.WebConstants;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class HttpWebUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpWebUtils.class);


    public static HttpServletRequest getRequest() {
        if (null == RequestContextHolder.getRequestAttributes()) {
            return null;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getRequest();
    }


    public static HttpServletResponse getResponse() {
        if (null == RequestContextHolder.getRequestAttributes()) {
            return null;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getResponse();
    }


    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (null != request) {
            return getRequest().getSession();
        }
        return null;
    }

    public static <T> T getSessionAttribute(String key, T value) {
        HttpSession session = getSession();
        if (null != session) {
            return (T) session.getAttribute(key);
        }
        return null;
    }

    public static String getParameter(ServletRequest servletRequest, String paramName, String defaultValue) {
        String value = servletRequest.getParameter(paramName);
        if (Objects.isNull(value)||value.isEmpty()) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static void destroySession() {
        getSession().invalidate();
    }

    public static String getSessionId() {
        return getSession().getId();
    }

    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }

    public static <T> void setSessionAttribute(String key, T value) {
        getSession().setAttribute(key, value);
    }

    public static <T> T getSessionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    public static String getClientIp() {
        return HttpRequestUtils.getIpAddress(getRequest());
    }

    public static String getClientIp2() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static Long getCurrentUserId() {
        try {
            return getSessionAttribute(WebConstants.USER_ID);
        }catch (Exception e){
            return null;
        }

    }

    public static void setCurrentUserId(Long userId) {
        setSessionAttribute(WebConstants.USER_ID, userId);
    }

    public static String getOpenId() {
        String openId = Cookies.getCookie(WebConstants.OPEN_ID, getRequest());
        if (Objects.isNull(openId)||openId.isEmpty()) {
            openId = getSessionAttribute(WebConstants.OPEN_ID);
        }
        return openId;
    }

    public static void setOpenId(String openId) {
        Cookies.setCookie(WebConstants.OPEN_ID, openId, -1, "/", getResponse());
        //session存储
        setSessionAttribute(WebConstants.OPEN_ID, openId);
    }

    public static String getCurrentPlatform() {
        try {
            return getSessionAttribute(WebConstants.PLATFORM);
        } catch (Exception e) {
            return null;
        }
    }
    public static void setCurrentPlatform(String platform) {
        setSessionAttribute(WebConstants.PLATFORM, platform);
    }

}
