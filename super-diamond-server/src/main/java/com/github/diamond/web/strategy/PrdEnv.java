package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

import java.util.Date;

/**
 * Created by qingyin on 2015/5/4.
 */
public class PrdEnv implements EnvStrategy{
    @Override
    public ConfProjectConfig setFieldValue(String configValue, String user) {
        ConfProjectConfig config=new ConfProjectConfig();
        config.setProductionValue(configValue);
        config.setProductionUser(user);
        config.setProductionTime(new Date());
        config.setOrderField("PRODUCTION_TIME");
        return config;
    }
    @Override
    public String getVersionField() {
        return "PRODUCTION_VERSION";
    }

    @Override
    public String getEnvField() {
        return "PRODUCTION_VALUE";
    }
    public String getModifyVersion(){
        return "PRODUCTION_VERSION=PRODUCTION_VERSION+1";
    }
    public String getOrderField(){
        return "PRODUCTION_TIME";
    }
}
