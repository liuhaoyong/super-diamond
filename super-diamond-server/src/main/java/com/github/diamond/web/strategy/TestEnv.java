package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

import java.util.Date;

/**
 * Created by qingyin on 2015/5/4.
 */
public class TestEnv implements EnvStrategy{
    @Override
    public ConfProjectConfig setFieldValue(String configValue, String user) {
        ConfProjectConfig config=new ConfProjectConfig();
        config.setTestValue(configValue);
        config.setTestUser(user);
        config.setTestTime(new Date());
        config.setOrderField("TEST_TIME");
        return config;
    }
    @Override
    public String getVersionField() {
        return "TEST_VERSION";
    }

    @Override
    public String getEnvField() {
        return "TEST_VALUE";
    }
    public String getModifyVersion(){
        return "TEST_VERSION=TEST_VERSION+1";
    }

    public String getOrderField(){
        return "TEST_TIME";
    }
}
