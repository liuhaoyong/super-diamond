package com.github.diamond.web.common;

import com.github.diamond.web.strategy.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有常量信息
 * Created by qingyin on 2015/5/4.
 */
public class SuperDiamondConstant {

    public final static String ENV_TEST="test";
    public final static String ENV_DEVELOPMENT="development";
    public final static String ENV_PRODUCTION="production";
    public final static String ENV_PRE="staging";

    /**删除状态**/
    public final static int STATUS_DEL=1;
    public final static int STATUS_UN_DEL=0;

    public final static String ADMIN="admin";

    /*配置环境*/
    public final static Map<String,EnvStrategy> envMap=new HashMap<String,EnvStrategy>(){
        {
            put(ENV_TEST,new TestEnv());
            put(ENV_DEVELOPMENT,new DevEnv());
            put(ENV_PRODUCTION,new PrdEnv());
            put(ENV_PRE,new PreEnv());
        }
    };


}
