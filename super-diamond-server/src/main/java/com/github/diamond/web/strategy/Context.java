package com.github.diamond.web.strategy;

import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;

/**
 * Created by qingyin on 2015/5/4.
 */
public class Context {
    private EnvStrategy envStrategy;
    public Context(EnvStrategy envStrategy){
        this.envStrategy=envStrategy;
    }
    public ConfProjectConfig setFieldValue(String configValue,String user){
        return this.envStrategy.setFieldValue(configValue,user);
    }

    public String getVersionField(){
        return this.envStrategy.getVersionField();
    }

    public String getEnvField(){return this.envStrategy.getEnvField();}

    public String getModifyVersion(){
        return this.envStrategy.getModifyVersion();
    }

    public String getOrderField(){return this.envStrategy.getOrderField();}

}
