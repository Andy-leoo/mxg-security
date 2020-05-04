package com.mxg.base.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author jiangxiao
 * @Title: MxgResult
 * @Package
 * @Description: 定义返回
 * @date 2020/3/2320:03
 */
@Data
public class MxgResult {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public MxgResult() {
    }

    public MxgResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public MxgResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public MxgResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static MxgResult ok() {
        return new MxgResult(null);
    }
    public static MxgResult ok(String message) {
        return new MxgResult(message, null);
    }
    public static MxgResult ok(Object data) {
        return new MxgResult(data);
    }
    public static MxgResult ok(String message, Object data) {
        return new MxgResult(message, data);
    }

    public static MxgResult build(Integer code, String message) {
        return new MxgResult(code, message, null);
    }

    public static MxgResult build(Integer code, String message, Object data) {
        return new MxgResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MxgResult 对象
     * @param json
     * @return
     */
    public static MxgResult format(String json) {
        try {
            return JSON.parseObject(json, MxgResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
