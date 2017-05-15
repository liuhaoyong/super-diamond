/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.web.service;

import com.github.diamond.web.model.ConfProjectModule;
import com.github.diamond.web.persistence.ConfProjectConfigMapper;
import com.github.diamond.web.persistence.ConfProjectModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Create on @2013-8-21 @下午8:18:44 
 * @author bsli@ustcinfo.com
 */
@Service
public class ModuleService {
	@Autowired
	private ConfProjectModuleMapper confProjectModuleMapper;
	@Autowired
	private ConfProjectConfigMapper confProjectConfigMapper;
	Logger logger= LoggerFactory.getLogger(this.getClass());
	/**
	 * 查询所有模块
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> queryModules(long projectId) {
		return this.confProjectModuleMapper.queryModuleByProjId(projectId);
	}
	
	@Transactional
	public void save(Long projectId, String name) {
		logger.info("begin - ModuleService.save method.projectId="+projectId+",name="+name+"");
        long id = 1;
		Long genId=this.confProjectModuleMapper.generatorId();
		if(genId!=null){
			id=genId.longValue();
		}
		ConfProjectModule module=new ConfProjectModule();
		module.setProjId(projectId);
		module.setModuleId(id);
		module.setModuleName(name);
		this.confProjectModuleMapper.insert(module);
	}
	
	@Transactional
	public boolean delete(long moduleId, long projectId) {
		logger.info("begin - ModuleService.delete method;moduleId="+moduleId+",projectId="+projectId+"");
		int count=this.confProjectConfigMapper.checkProjConfExist(projectId,moduleId);
		logger.info("-ModuleService.delete;count="+count);
		if(count == 0) { //如果模块存在关联关系，则不能删除，否则删之
			this.confProjectModuleMapper.delByModuleAndProjId(moduleId,projectId);
			return true;
		} else {
			return false;
		}
	}

	public boolean checkModuleNameExist(String moduleName){
		logger.info("begin - ModuleService.checkModuleNameExist method;moduleName="+moduleName+"");
		int count=this.confProjectModuleMapper.checkModuleNameExist(moduleName);
		if(count>0){
			return true;
		}
		return false;
	}
}
