package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

/**
 * 策略模式,控制环境类别
 * Created by liuhaoyong on 2015/5/4.
 */
public interface EnvStrategy {

    public ConfProjectConfig setFieldValue(String configValue,String user);

    public String getVersionField();

    public String getEnvField();

    public String getModifyVersion();

    public String getOrderField();
}
