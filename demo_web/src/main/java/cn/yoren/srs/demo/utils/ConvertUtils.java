package cn.yoren.srs.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zsq
 * @create 2019-07-09-16:55
 */
public class ConvertUtils {
    public ConvertUtils() {
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.convertValue(fromValue, toValueType);
    }
}
