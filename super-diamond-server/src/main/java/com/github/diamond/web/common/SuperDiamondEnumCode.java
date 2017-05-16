package com.github.diamond.web.common;

/**
 * 返回错误码信息，处理公共code
 * Created by liuhaoyong on 2015/5/4.
 */
public enum SuperDiamondEnumCode {

    USER_CANCEL("022001","用户已经被注销"),
    USER_PWD_INCORRECT("022002","登录失败，用户密码不正确"),
    USER_NOT_EXISTS("022003","登录失败，用户不存在"),

    CONFIG_UNABLE_UPDATE("022004","Config Key 不能修改"),
    CONFIG_ALREADY_UPDATE("022005","配置文件已被其它用户更新，请重新编辑"),
    MODULE_UNABLE_UPDATE("022006","模块不能修改"),
    MODULE_ALREADY_RELATIONS("022007","模块已经被配置项关联，不能删除！"),

    OPERA_DELETE_SUCCES("022008","删除成功"),
    OPERA_UPDATE_SUCCESS("022009","修改成功"),
    OPERA_INSERT_SUCCESS("022010","添加成功"),
    OPERA_UNAUTHORIZED("022011","无权删除项目"),
    OPERA_CHOOSE_USER_ROLE("022012","请选择用户角色"),

    OLD_PASSWORD_INCORRECT("022013","原密码不正确"),
    PASSWORD_UPDATE_SUCCESS("022014","密码修改成功"),

    PROJECT_VERIFY_NOTNULL("022015","项目编码不能为空"),
    PROJECT_VERIFY_NAME_NOTNULL("022016","项目名称不能为空"),
    PROJECT_VERIFY_USERCODE_NOTNULL("022017","项目管理者不能为空"),
    PROJECT_VERIFY_USER_NOTEXIST("022018","项目管理者不存在，请检查拼写是否正确"),

    DATABASE_CONNECT_FAILED("022019","不能获取数据库连接，请联系管理员！");


    private final String code;
    private final String msg;

    private SuperDiamondEnumCode(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    private SuperDiamondEnumCode(String code,String msg,String detailDesc){
        this.code=code;
        this.msg=msg;
    }

    public String getCode(){return code;}
    public String getMsg(){return msg;}

    public String getMsg(String detailDesc){return msg+" : "+detailDesc;}

    public static SuperDiamondEnumCode getByKey(String code) {
        if (code == null)
            return null;
        code = code.trim();
        for (SuperDiamondEnumCode value : values()) {
            if (value.getCode().equals(code))
                return value;
        }
        return null;
    }
}
