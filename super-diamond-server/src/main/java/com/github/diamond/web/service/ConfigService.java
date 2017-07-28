/**
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */
package com.github.diamond.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.diamond.utils.Page;
import com.github.diamond.web.common.SuperDiamondConstant;
import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.persistence.ConfHistoryMapper;
import com.github.diamond.web.persistence.ConfProjectConfigMapper;
import com.github.diamond.web.strategy.Context;
import com.github.diamond.web.vo.ConfProjModuleVO;

/**
 * Create on @2013-8-23 @上午10:26:17
 *
 * @author bsli@ustcinfo.com
 */
@Service
public class ConfigService {
    Logger                          logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectService          projectService;
    @Autowired
    private ConfProjectConfigMapper confProjectConfigMapper;
    @Autowired
    private ConfHistoryMapper       confHistoryMapper;

    public Page<ConfProjModuleVO> queryConfigs(Long projectId, Long moduleId, int pageNum, int pageSize,
                                               String confKey) {
        int totalCount = this.confProjectConfigMapper.queryConfigsCount(projectId, moduleId, confKey);
        Page<ConfProjModuleVO> page = new Page<ConfProjModuleVO>(pageNum, pageSize, totalCount);
        List<ConfProjModuleVO> list = this.confProjectConfigMapper.queryConfigs(projectId, moduleId,
                page.getFirstResult(), page.getPageSize(), confKey);
        page.setResult(list);
        return page;
    }

    public String queryConfigs(String projectCode, String type) {
        List<Map<String, Object>> configs = this.confProjectConfigMapper.queryConfigsFor3Tbl(projectCode);

        return viewConfig(configs, type);
    }

    @Transactional
    public void insertConfig(String configKey, String configValue, String configDesc, Long projectId, Long moduleId,
                             String user) {
        logger.info("begin - ConfigService.insertConfig method");
        long id = 1;
        Long genId = this.confProjectConfigMapper.generatorId();
        if (genId != null) {
            id = genId.longValue();
        }
        ConfProjectConfig config = ConfProjectConfig.loadConfigData(configKey, configValue, configDesc, projectId,
                moduleId, user);
        config.setConfigId(id);
        this.confProjectConfigMapper.insert(config);
        projectService.updateVersion(projectId);
    }

    @Transactional
    public void addNewAndDelOldConfig(String configKey, String configValue, String configDesc, Long projectId,
                                      Long moduleId, String user, Long oldConfigId) {
        insertConfig(configKey, configValue, configDesc, projectId, moduleId, user);
        deleteConfig(oldConfigId, projectId);
    }

    /**
     * 修改配置信息
     *
     * @param type
     * @param configId
     * @param configKey
     * @param configValue
     * @param configDesc
     * @param projectId
     * @param moduleId
     * @param user
     */
    @Transactional
    public void updateConfig(String type, Long configId, String configKey, String configValue, String configDesc,
                             Long projectId, Long moduleId, String user, String oldValue) {
        logger.info("begin - ConfigService.updateConfig,type=" + type + "");
        Context context = new Context(SuperDiamondConstant.envMap.get(type));
        ConfProjectConfig config = context.setFieldValue(configValue, user);
        config.setConfigKey(configKey);
        config.setConfigDesc(configDesc);
        config.setProjectId(projectId);
        config.setModuleId(moduleId);
        config.setConfigId(configId);
        this.confProjectConfigMapper.update(config);
        projectService.updateVersion(projectId, type);
        ConfHistory history = new ConfHistory();
        history.setConfigId(configId);
        history.setConfigKey(configKey);
        history.setConfigValue(oldValue);
        history.setOptTime(new Date());
        history.setOptUser(user);
        history.setType(type);
        saveHistoryRecord(history);
        logger.info("end - ConfigService.updateConfig");
    }

    /**
     * 增加新配置，删除老配置
     * 
     * @param type
     * @param configId
     * @param configKey
     * @param configValue
     * @param configDesc
     * @param projectId
     * @param moduleId
     * @param user
     * @param oldValue
     */
    @Transactional
    public void updateAndDelConfig(String type, Long configId, String configKey, String configValue, String configDesc,
                                   Long projectId, Long moduleId, String user, String oldValue, Long oldConfigId) {
        updateConfig(type, configId, configKey, configValue, configDesc, projectId, moduleId, user, oldValue);
        deleteConfig(oldConfigId, projectId);
    }

    private void saveHistoryRecord(ConfHistory history) {
        //根据configId和type查询当前配置的历史记录条数
        List<ConfHistory> histories = this.confHistoryMapper.queryConfHisByConfId(history);
        if (histories != null && histories.size() > 3) {
            //超过5条的历史记录清除
            List<Long> hisIds = new ArrayList<>();
            for (int i = 0; i < histories.size(); i++) {
                if (i > 3) {
                    hisIds.add(histories.get(i).getHisId());
                }
            }
            if (hisIds.size() > 0) {
                this.confHistoryMapper.deleteByBatch(hisIds);
            }
        }
        confHistoryMapper.insert(history);
    }

    /**
     * 删除配置
     *
     * @param id
     * @param projectId
     */
    public void deleteConfig(Long id, Long projectId) {
        logger.info("begin - ConfigService.deleteConfig,id=" + id + "");
        this.confProjectConfigMapper.updateDeleteFlag(SuperDiamondConstant.STATUS_DEL, id);
        projectService.updateVersion(projectId);
    }

    private String viewConfig(List<Map<String, Object>> configs, String type) {
        String message = "";
        boolean versionFlag = true;
        for (Map<String, Object> map : configs) {
            Context context = new Context(SuperDiamondConstant.envMap.get(type));
            if (versionFlag) {
                message += "#version = " + map.get(context.getVersionField()) + "\r\n";
                versionFlag = false;
            }
            //玄武  注释掉描述，可能导致描述解析出key 和 value
            //            String desc = (String) map.get("CONFIG_DESC");
            //            if (StringUtils.isNotBlank(desc))
            //                message += "#" + desc + "\r\n";
            String value = getValue((String) map.get(context.getEnvField()));
            if (value != null) {
                message += map.get("MODULE_NAME") + "." + map.get("CONFIG_KEY") + " = "
                        + value.replaceAll("\r\n", " ").replaceAll("\n", " ") + "\r\n";
            }
        }
        message += "#end#\r\n";
        return message;
    }

    private String getValue(String value) {
        if (value == null) {
            return null;
        }

        String _value = value.replaceAll("\r\n", " ").replaceAll("\n", " ");
        if (StringUtils.isNotBlank(_value)) {
            return _value;
        }

        return null;
    }

    /**
     * 查询所有配置信息
     *
     * @param projectId
     * @param moduleId
     * @param configKey
     * @return
     */
    public List<ConfProjectConfig> getConfigs(Long projectId, Long moduleId, String configKey) {
        logger.info("begin - ConfigService-getConfigs,projectId=" + projectId + "");
        ConfProjectConfig record = new ConfProjectConfig();
        record.setDeleteFlag(SuperDiamondConstant.STATUS_UN_DEL);
        record.setProjectId(projectId);
        record.setModuleId(moduleId);
        record.setConfigKey(configKey);
        return this.confProjectConfigMapper.queryConfigInfo(record);
    }

    public ConfProjectConfig getConfig(Long configId) {
        return this.confProjectConfigMapper.getAvailableConfig(configId);
    }

    public String getVersionField(String type) {
        Context context = new Context(SuperDiamondConstant.envMap.get(type));
        return context.getVersionField();
    }

    public List<Map<String, String>> getAllKeysByProjIdAndModuleId(Long projectId, Long moduleId) {
        return this.confProjectConfigMapper.getAllKeysByProjIdAndModuleId(projectId, moduleId);
    }
}
