package com.github.diamond.web.vo;

import java.util.Date;

/**
 * Created by qingyin on 2015/5/4.
 */
public class ConfProjModuleVO {
    private Long configId;
    private String configKey;
    private String configValue;
    private String configDesc;
    private Long projectId;
    private Long moduleId;
    private Long deleteFlag;
    private String optUser;
    private Date optTime;
    private String productionValue;
    private String productionUser;
    private Date productionTime;
    private String testValue;
    private String testUser;
    private Date testTime;
    private String preValue;
    private String preUser;
    private Date preTime;
    /**
     * ******conf_project_module************
     */
    private Long pjModuleId;
    private Long projId;
    private String moduleName;
    /**
     * ******conf_project***********
     */
    private Long id;
    private String projCode;
    private String projName;
    private Long ownerId;
    private Long developmentVersion;
    private Long productionVersion;
    private Long preVersion;
    private Long testVersion;
    private Long prjDeleteFlag;
    private Date createTime;
    private Date updateTime;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Long deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getProductionValue() {
        return productionValue;
    }

    public void setProductionValue(String productionValue) {
        this.productionValue = productionValue;
    }

    public String getProductionUser() {
        return productionUser;
    }

    public void setProductionUser(String productionUser) {
        this.productionUser = productionUser;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getTestUser() {
        return testUser;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getPreUser() {
        return preUser;
    }

    public void setPreUser(String preUser) {
        this.preUser = preUser;
    }

    public Date getPreTime() {
        return preTime;
    }

    public void setPreTime(Date preTime) {
        this.preTime = preTime;
    }

    public Long getPjModuleId() {
        return pjModuleId;
    }

    public void setPjModuleId(Long pjModuleId) {
        this.pjModuleId = pjModuleId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getDevelopmentVersion() {
        return developmentVersion;
    }

    public void setDevelopmentVersion(Long developmentVersion) {
        this.developmentVersion = developmentVersion;
    }

    public Long getProductionVersion() {
        return productionVersion;
    }

    public void setProductionVersion(Long productionVersion) {
        this.productionVersion = productionVersion;
    }

    public Long getPreVersion() {
        return preVersion;
    }

    public void setPreVersion(Long preVersion) {
        this.preVersion = preVersion;
    }

    public Long getTestVersion() {
        return testVersion;
    }

    public void setTestVersion(Long testVersion) {
        this.testVersion = testVersion;
    }

    public Long getPrjDeleteFlag() {
        return prjDeleteFlag;
    }

    public void setPrjDeleteFlag(Long prjDeleteFlag) {
        this.prjDeleteFlag = prjDeleteFlag;
    }
}