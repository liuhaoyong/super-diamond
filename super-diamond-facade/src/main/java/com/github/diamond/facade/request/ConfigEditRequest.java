package com.github.diamond.facade.request;

import org.apache.commons.lang3.StringUtils;

import com.github.diamond.facade.common.Printable;

/**
 * ConfigEditRequest.java功能描述：配置编辑请求
 * 
 * @author quyinjun 2017年7月27日 上午10:02:53
 */
public class ConfigEditRequest extends Printable {
    // 配置信息
    private String type = "development";
    private String projCode;
    private String moduleName;
    private String configKey;
    private String oldConfigKey;
    private String configValue;
    private String configDesc;

    // 用户信息
    private String userCode;
    private String md5Passwd;

    public String opType() {
        if (StringUtils.isBlank(this.getConfigKey()) && StringUtils.isBlank(this.getOldConfigKey())) {
            return "";
        }
        if (StringUtils.isNotBlank(this.getConfigKey()) && StringUtils.isBlank(this.getOldConfigKey())) {
            return "ADD";
        }
        if (StringUtils.equalsIgnoreCase(this.getConfigKey(), this.getOldConfigKey())) {
            return "UPDATE";
        }
        if (StringUtils.isNotBlank(this.getConfigKey()) && StringUtils.isNotBlank(this.getOldConfigKey())
                && !StringUtils.equalsIgnoreCase(this.getConfigKey(), this.getOldConfigKey())) {
            return "DEL_AND_ADD";
        }
        if (StringUtils.isBlank(this.getConfigKey()) || StringUtils.isNotBlank(this.getOldConfigKey())) {
            return "DEL";
        }
        return "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMd5Passwd() {
        return md5Passwd;
    }

    public void setMd5Passwd(String md5Passwd) {
        this.md5Passwd = md5Passwd;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getOldConfigKey() {
        return oldConfigKey;
    }

    public void setOldConfigKey(String oldConfigKey) {
        this.oldConfigKey = oldConfigKey;
    }
}
