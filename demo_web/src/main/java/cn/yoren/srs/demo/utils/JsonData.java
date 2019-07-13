package cn.yoren.srs.demo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zsq
 * @create 2019-07-09-16:48
 */
public final class JsonData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code = "";
    private String state = "";
    private String msg = "";
    private Object data = null;
    private String jwt = "";

    public JsonData() {
    }

    public static JsonData error() {
        return error("操作失败");
    }

    public static JsonData error(String msg) {
        return error(ErrorEnum.E_900.getErrorCode(), msg);
    }

    public static JsonData error(ErrorEnum errorEnum) {
        Assert.notNull(errorEnum, "errorEnum is required");
        return error(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
    }

    public static JsonData error(ErrorEnum errorEnum, String msg) {
        Assert.notNull(errorEnum, "errorEnum is required");
        return StringUtils.isNotBlank(msg) ? error(errorEnum.getErrorCode(), msg) : error(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
    }

    public static JsonData error(String code, String msg) {
        return error(code, msg, (Object)null);
    }

    public static JsonData error(String code, String msg, Object objData) {
        JsonData r = new JsonData();
        r.setCode(code);
        r.setData(objData);
        r.setMsg(msg);
        r.setState("false");
        LoggerUtils.infoWithStackTrace("JsonData.error--->:" + msg);
        return r;
    }

    public static JsonData ok() {
        return ok("操作成功");
    }

    public static JsonData oknomsg() {
        return ok("");
    }

    public static JsonData oking() {
        return ok(ErrorEnum.E_20200.getErrorCode(), ErrorEnum.E_20200.getErrorMsg());
    }

    public static JsonData ok(String msg) {
        return ok(ErrorEnum.E_200.getErrorCode(), msg);
    }

    public static JsonData ok(String code, String msg) {
        return ok(code, msg, (Object)null);
    }

    public static JsonData ok(Object objData) {
        return ok(ErrorEnum.E_200.getErrorCode(), ErrorEnum.E_200.getErrorMsg(), objData);
    }

    public static JsonData ok(String code, String msg, Object objData) {
        JsonData r = new JsonData();
        r.setCode(code);
        r.setData(objData);
        r.setMsg(msg);
        r.setState("true");
        return r;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    public String getCode() {
        return this.code;
    }

    public JsonData setCode(String code) {
        this.code = code;
        return this;
    }

    public String getState() {
        return this.state;
    }

    public JsonData setState(String state) {
        this.state = state;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public JsonData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public JsonData setData(Object data) {
        this.data = data;
        return this;
    }

    public String getJwt() {
        return this.jwt;
    }

    public JsonData setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public boolean success() {
        return StringUtils.isNotBlank(this.getState()) && this.getState().equals("true");
    }

    public <T> List<T> convertDataToList(Class<T> clazz) {
        List<T> list = new ArrayList();
        List<T> tmpList = (List)this.getData();
        if (tmpList != null) {
            Iterator var4 = tmpList.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                list.add(ConvertUtils.convertValue(obj, clazz));
            }
        }

        return list;
    }
}