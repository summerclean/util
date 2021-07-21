package work.sihai.common.config.mybatis;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class BaseResponse<T> implements Serializable {
    private static final String SUCCESS_CODE = "1";               //获取数据成功状态码
    private static final String ERROR_CODE = "0";                 //获取数据出错状态码
    private static final String PARAM_ERROR_CODE = "0";           //参数传递错误状态码
    private static final String BUSINESS_ERROR_CODE = "-101";   //业务异常
    private static final String NO_LOGIN = "-99";   //未登录

    private static final String SUCCESS_MESSAGE = "请求数据成功!";          //获取数据成功
    private static final String ERROR_MESSAGE = "请求数据出错!";            //获取数据出错
    private static final String PARAM_ERROR_MESSAGE = "请求参数错误!"; //参数传递错误

    private String _status = "status";
    private String _detail = "detail";

    @ApiModelProperty(value = "响应结果数据")
    private T data;

    private Map<String, Object> _this = Maps.newHashMap();

    public BaseResponse() {

    }

    public boolean isSuccess() {
        return Objects.equals(getStatus(), SUCCESS_CODE);
    }

    public BaseResponse(String status) {
        this(status, null);
    }

    public BaseResponse(String status, T data) {
        this(status, data, "");
    }

    private BaseResponse(String status, T data, String detail) {
        this.setDetail(detail);
        this.setStatus(status);
        this.setData(data);
    }

    public static BaseResponse success() {
        return newInstance(SUCCESS_CODE, null, SUCCESS_MESSAGE);
    }

    public static <E>BaseResponse<E> success(E data) {
        return newInstance(SUCCESS_CODE, data, SUCCESS_MESSAGE);
    }

    public static BaseResponse error() {
        return newInstance(ERROR_CODE, null, ERROR_MESSAGE);
    }

    public static BaseResponse error(String msg) {
        return newInstance(ERROR_CODE, null, msg);
    }

    public static BaseResponse paramError() {
        return newInstance(PARAM_ERROR_CODE, null, PARAM_ERROR_MESSAGE);
    }

    public static BaseResponse paramError(String msg) {
        return newInstance(PARAM_ERROR_CODE, null, msg);
    }

    public static BaseResponse bussinessError(String msg) {
        return newInstance(BUSINESS_ERROR_CODE, null, msg);
    }

    public static <E>BaseResponse<E> bussinessError(E data, String msg) {
        return newInstance(BUSINESS_ERROR_CODE, data, msg);
    }

    public static final BaseResponse no_login() {
        return newInstance(NO_LOGIN, null, "请登录!");
    }


    public static BaseResponse newInstance(String status) {
        return new BaseResponse(status);
    }

    public static <E>BaseResponse<E> newInstance(String status, E data) {
        return new BaseResponse(status, data);
    }

    public static <E>BaseResponse<E> newInstance(String status, E data, String detail) {
        return new BaseResponse(status, data, detail);
    }

    @ApiModelProperty(value = "响应状态码: [ 1:获取数据成功, 0:获取数据出错, -101:业务异常,-99未登录 ] ")
    public String getStatus() {
        return (String) _this.get(_status);
    }

    public BaseResponse setStatus(String status) {
        _this.put(this._status, Strings.nullToEmpty(status));
        return this;
    }

    public T getData() {
        return this.data;
    }

    public BaseResponse setData(T data) {
        this.data = data;
        return this;
    }

    @ApiModelProperty(value = "响应结果描述: [ 请求数据成功 /请求数据出错 /请求参数错误 /自定义描述 ]")
    public String getDetail() {
        return (String) _this.get(_detail);
    }

    public BaseResponse setDetail(String message) {
        _this.put(_detail, Strings.nullToEmpty(message));
        return this;
    }
}