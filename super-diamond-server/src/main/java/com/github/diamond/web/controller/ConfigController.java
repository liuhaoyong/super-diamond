/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.diamond.netty.DiamondServerHandler;
import com.github.diamond.utils.SessionHolder;
import com.github.diamond.web.common.SuperDiamondEnumCode;
import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.model.ConfUser;
import com.github.diamond.web.service.ConfigService;
import com.github.diamond.web.service.HistoryService;
import com.github.diamond.web.service.ProjectService;

/**
 * Create on @2013-8-23 @上午11:46:19 
 * @author bsli@ustcinfo.com
 */
@Controller
public class ConfigController extends BaseController {
	@Autowired
	private ConfigService configService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DiamondServerHandler diamondServerHandler;
	@Autowired
	private HistoryService historyService;

	@RequestMapping("/config/save")
	public String saveConfig(String type, Long configId, String configKey, String configValue, 
			String configDesc, Long projectId, Long moduleId, Long selModuleId, Long version,String oldValue,
			String qryKey,Integer pgNum) {
		ConfUser user = (ConfUser) SessionHolder.getSession().getAttribute("sessionUser");
		
		//检查key是否重复
		List<ConfProjectConfig> configs = configService.getConfigs(projectId, moduleId, configKey);
		if(configs != null && configs.size() > 0) {
            for(ConfProjectConfig config:configs) {
                System.err.println("configId=" + configId + "|config.get(\"CONFIG_ID\")=" + config.getConfigId());
                if(configId == null || configId.longValue() != (config.getConfigId()).longValue()) {
                    throw new RuntimeException("key: " + configKey + " 已存在！");
                }
            }
        }

        if (configId != null) {
			ConfProjectConfig config = configService.getConfig(configId);
            //不让修改 moduleId 和 configKey
            if (!configKey.equals(config.getConfigKey())) {
                throw new RuntimeException(SuperDiamondEnumCode.CONFIG_UNABLE_UPDATE.getMsg());
            }
            if (moduleId.longValue() != (config.getModuleId()).longValue()) {
                throw new RuntimeException(SuperDiamondEnumCode.MODULE_UNABLE_UPDATE.getMsg());
            }
        }
        //检查version
		Map<String,Object> project = projectService.queryProject(projectId);

		String versionColumn = this.configService.getVersionField(type);
		if(version.longValue() != ((Integer)project.get(versionColumn)).longValue()) {
			throw new RuntimeException(SuperDiamondEnumCode.CONFIG_ALREADY_UPDATE.getMsg());
		}
		if(configId == null) {
			configService.insertConfig(configKey, configValue, configDesc, projectId, moduleId, user.getUserCode());
		} else {
			configService.updateConfig(type, configId, configKey, configValue, configDesc, projectId, moduleId, user.getUserCode(),oldValue);
		}
		pgNum=(pgNum==null)?1:pgNum;
		qryKey=StringUtils.isBlank(qryKey)?"":qryKey;
		if(selModuleId!=null) {
			return "redirect:/profile/" + type + "/" + projectId + "?moduleId=" + selModuleId + "&confKey=" + qryKey + "&pageNumber=" + pgNum + "";
		}
		return "redirect:/profile/" + type + "/" + projectId + "?confKey=" + qryKey + "&pageNumber=" + pgNum + "";
	}
	
	@RequestMapping("/config/delete/{id}")
	public String deleteConfig(String type, Long projectId, @PathVariable Long id) {
        //停用删除功能 女娲石 2014-06－11
		//configService.deleteConfig(id, projectId);
		
//		String projCode = (String)projectService.queryProject(projectId).get("PROJ_CODE");
//		String config = configService.queryConfigs(projCode, type);
//		diamondServerHandler.pushConfig(projCode, type, config);
		return "redirect:/profile/" + type + "/" + projectId;
	}
	@RequestMapping("/config/history/{type}/{configId}")
	public String showHistory(@PathVariable String type,@PathVariable Long configId,String version,String projectId,ModelMap modelMap){
		logger.info("begin showHistory");
		List<ConfHistory> voList=this.historyService.getHistoryRecordByConfId(configId,type);
		modelMap.addAttribute("hisList",voList);
		modelMap.addAttribute("version",version);
		modelMap.addAttribute("projectId",projectId);
		return "profile/history";
	}

	@RequestMapping("/config/rollback/{hisId}/{configId}")
	public String rollBackConfig(@PathVariable Long hisId,@PathVariable Long configId,String qryKey,Integer pgNum,
								 Long projectId,String type,String moduleId,Long version,String hisValue){
		ConfUser user = (ConfUser) SessionHolder.getSession().getAttribute("sessionUser");
		//检查version
		Map<String,Object> project = projectService.queryProject(projectId);

		String versionColumn = this.configService.getVersionField(type);
		if(version.longValue() != ((Integer)project.get(versionColumn)).longValue()) {
			throw new RuntimeException(SuperDiamondEnumCode.CONFIG_ALREADY_UPDATE.getMsg());
		}
		this.historyService.rollbackConfig(hisId,configId,type,user.getUserCode());
		return "redirect:/profile/" + type + "/" + projectId + "?moduleId=" + moduleId+"&confKey="+qryKey+"&pageNumber="+pgNum+"";
	}
}
