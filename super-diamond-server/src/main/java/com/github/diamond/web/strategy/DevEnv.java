package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

import java.util.Date;

/**
 * Created by qingyin on 2015/5/4.
 */
public class DevEnv implements EnvStrategy {
    @Override
    public ConfProjectConfig setFieldValue(String configValue, String user) {
        ConfProjectConfig config=new ConfProjectConfig();
        config.setConfigValue(configValue);
        config.setOptUser(user);
        config.setOptTime(new Date());
        config.setOrderField("OPT_TIME");
        return config;
    }

    @Override
    public String getVersionField() {
        return "DEVELOPMENT_VERSION";
    }

    @Override
    public String getEnvField() {
        return "CONFIG_VALUE";
    }

    public String getModifyVersion(){
        return "DEVELOPMENT_VERSION=DEVELOPMENT_VERSION+1";
    }

    public String getOrderField(){
        return "OPT_TIME";
    }
}
