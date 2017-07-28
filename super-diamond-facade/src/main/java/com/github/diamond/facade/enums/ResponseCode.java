package com.github.diamond.facade.enums;

public enum ResponseCode {

    SUCCESS("OK"),
    FAIL("业务处理异常"),
    NOT_FOUND_USER("用户不存在"),
    ERROR_PASSWORD("密码校验不通过"),
    NOT_FOUND_PROJECT("项目不存在"),
    NOT_FOUND_MODULE("模块不存在"),
    MUTIPLE_CONFIG("找到了多条配置"),
    NOT_FOUND_CONFIG("未找到相应配置"),
    CANNOT_ADD_SAME_CONFIG("不允许新增同样配置"),
    NOT_FOUND_OLD_CONFIG("没有找到老的配置"),
    MUTIPLE_OLD_CONFIG("找到了多条老配置"),
    DO_NOTHING("没有做任何事情");

    private String desc;

    ResponseCode(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return this.name();
    }

    public String getDesc() {
        return desc;
    }

}
