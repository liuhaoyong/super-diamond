package com.github.diamond.facade.response;

import com.github.diamond.facade.common.Printable;

public class BaseResponse extends Printable {
    /**
     * 返回码
     */
    private String code = "SUCCESS";

    /**
     * 返回消息
     */
    private String msg  = "OK";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
