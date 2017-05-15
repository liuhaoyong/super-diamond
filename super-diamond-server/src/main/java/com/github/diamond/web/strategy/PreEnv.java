package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

import java.util.Date;

/**
 * Created by qingyin on 2015/5/4.
 */
public class PreEnv implements EnvStrategy{
    @Override
    public ConfProjectConfig setFieldValue(String configValue, String user) {
        ConfProjectConfig config=new ConfProjectConfig();
        config.setPreValue(configValue);
        config.setPreUser(user);
        config.setPreTime(new Date());
        config.setOrderField("PRE_TIME");
        return config;
    }
    @Override
    public String getVersionField() {
        return "PRE_VERSION";
    }

    @Override
    public String getEnvField() {
        return "PRE_VALUE";
    }
    public String getModifyVersion(){
        return "PRE_VERSION=PRE_VERSION+1";
    }

    public String getOrderField(){
        return "PRE_TIME";
    }
}
