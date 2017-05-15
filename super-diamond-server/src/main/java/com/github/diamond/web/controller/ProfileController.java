/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.diamond.netty.DiamondServerHandler;
import com.github.diamond.web.service.ConfigService;
import com.github.diamond.web.service.ModuleService;
import com.github.diamond.web.service.ProjectService;


/**
 * Create on @2013-8-21 @下午6:55:09 
 * @author bsli@ustcinfo.com
 */
@Controller
public class ProfileController extends BaseController {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DiamondServerHandler diamondServerHandler;
	
	@RequestMapping("/profile/{type}/{projectId}")
	public String profile(@PathVariable("type") String type, @PathVariable("projectId") Long projectId,Long moduleId,Integer pageNumber,String confKey,ModelMap modelMap) {
		pageNumber=pageNumber==null?1:pageNumber;
		modelMap.addAttribute("modules", moduleService.queryModules(projectId));
		modelMap.addAttribute("pagelist", configService.queryConfigs(projectId, moduleId,pageNumber,pageSize,confKey));
		modelMap.addAttribute("moduleId", moduleId);
		modelMap.addAttribute("project", projectService.queryProject(projectId));
		modelMap.addAttribute("projectId",projectId);
		modelMap.addAttribute("moduleId",moduleId);
		modelMap.addAttribute("confKey",confKey);
		modelMap.addAttribute("pageNumber",pageNumber);
		return "profile/" + type;
	}
	
	@RequestMapping("/profile/preview/{projectCode}/{type}")
	public String preview(@PathVariable("type") String type, @PathVariable("projectCode") String projectCode, 
			Long projectId, ModelMap modelMap) {
		String config = configService.queryConfigs(projectCode, type);
		
		modelMap.addAttribute("project", projectService.queryProject(projectId));
		modelMap.addAttribute("message", config);
		return "profile/preview";
	}
	
	@RequestMapping("/profile/deploy/{projectCode}/{type}")
	public String deploy(@PathVariable("type") String type, @PathVariable("projectCode") String projectCode, 
			Long projectId, ModelMap modelMap) {
		String config = configService.queryConfigs(projectCode, type);
		String messageString = diamondServerHandler.pushConfig(projectCode, type, config);
		modelMap.addAttribute("project", projectService.queryProject(projectId));
		modelMap.addAttribute("message", messageString);
		return "profile/deployment";
	}

	@RequestMapping("/profile/json/{projectId}")
	@ResponseBody
	public List<Map<String,String>> profileJson(@PathVariable("projectId") Long projectId,Long moduleId){
		logger.info("begin profileJson");
		List<Map<String,String>> mapList=this.configService.getAllKeysByProjIdAndModuleId(projectId,moduleId);
		return mapList;
	}
}
