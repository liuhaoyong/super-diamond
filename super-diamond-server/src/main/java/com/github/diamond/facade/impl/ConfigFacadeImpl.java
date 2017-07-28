package com.github.diamond.facade.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.diamond.facade.ConfigFacade;
import com.github.diamond.facade.enums.ResponseCode;
import com.github.diamond.facade.request.ConfigEditRequest;
import com.github.diamond.facade.response.ConfigEditResponse;
import com.github.diamond.web.model.ConfProject;
import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.model.ConfProjectModule;
import com.github.diamond.web.model.ConfUser;
import com.github.diamond.web.service.ConfigService;
import com.github.diamond.web.service.ModuleService;
import com.github.diamond.web.service.ProjectService;
import com.github.diamond.web.service.UserService;

@Controller
@RequestMapping(value = "/tuhu_configuration_center")
public class ConfigFacadeImpl implements ConfigFacade {
    protected static final Logger logger = LoggerFactory.getLogger(ConfigFacadeImpl.class);
    @Autowired
    private ProjectService        projectService;
    @Autowired
    private ModuleService         moduleService;
    @Autowired
    private ConfigService         configService;
    @Autowired
    private UserService           userService;

    // TODO:进行并发版本控制
    @RequestMapping(value = "/edit", produces = "application/json")
    @ResponseBody
    @Override
    public ConfigEditResponse edit(@RequestBody ConfigEditRequest request) {
        logger.info("ConfigEditRequest:{}", request);
        ConfigEditResponse response = new ConfigEditResponse();
        try {
            // 校验用户
            ConfUser user = (ConfUser) userService.fetch(request.getUserCode(), request.getMd5Passwd());
            logger.info("ConfigEditRequest ConfUser:{}", user);
            if (null == user) {
                response.setCode(ResponseCode.NOT_FOUND_USER.getName());
                response.setMsg(ResponseCode.NOT_FOUND_USER.getDesc());
                return response;
            }
            if (!user.authPwd(request.getMd5Passwd())) {
                response.setCode(ResponseCode.ERROR_PASSWORD.getName());
                response.setMsg(ResponseCode.ERROR_PASSWORD.getDesc());
                return response;
            }
            // 校验项目
            ConfProject proj = projectService.queryByProjCode(request.getProjCode());
            logger.info("ConfigEditRequest ConfProject:{}", proj);
            if (null == proj) {
                response.setCode(ResponseCode.NOT_FOUND_PROJECT.getName());
                response.setMsg(ResponseCode.NOT_FOUND_PROJECT.getDesc());
                return response;
            }
            // 校验模块
            ConfProjectModule module = moduleService.queryModules(proj.getId(), request.getModuleName());
            logger.info("ConfigEditRequest ConfProjectModule:{}", module);
            if (null == module) {
                response.setCode(ResponseCode.NOT_FOUND_MODULE.getName());
                response.setMsg(ResponseCode.NOT_FOUND_MODULE.getDesc());
                return response;
            }

            List<ConfProjectConfig> configs = configService.getConfigs(proj.getId(), module.getModuleId(),
                    request.getConfigKey());
            logger.info("ConfigEditRequest already exist ConfProjectConfig:{}", configs);
            logger.info("ConfigEditRequest opType:{}", request.opType());

            // 操作风险：调用方根据业务指定是增加，更新还是删除，服务方校验是否符合对应操作类型
            switch (request.opType()) {
                case "ADD":
                    if ((null != configs) && (configs.size() > 0)) {
                        response.setCode(ResponseCode.CANNOT_ADD_SAME_CONFIG.getName());
                        response.setMsg(ResponseCode.CANNOT_ADD_SAME_CONFIG.getDesc());
                        return response;
                    } else {
                        configService.insertConfig(request.getConfigKey(), request.getConfigValue(),
                                request.getConfigDesc(), proj.getId(), module.getModuleId(), user.getUserCode());
                    }
                    break;
                case "UPDATE":
                    if ((null == configs) || configs.isEmpty()) {
                        response.setCode(ResponseCode.NOT_FOUND_CONFIG.getName());
                        response.setMsg(ResponseCode.NOT_FOUND_CONFIG.getDesc());
                        return response;
                    } else if (configs.size() > 1) {
                        response.setCode(ResponseCode.MUTIPLE_CONFIG.getName());
                        response.setMsg(ResponseCode.MUTIPLE_CONFIG.getDesc());
                        return response;
                    } else {
                        configService.updateConfig(request.getType(), configs.get(0).getConfigId(),
                                request.getConfigKey(), request.getConfigValue(), request.getConfigDesc(), proj.getId(),
                                module.getModuleId(), user.getUserCode(), request.getOldConfigKey());
                    }
                    break;
                case "DEL_AND_ADD":
                    List<ConfProjectConfig> oldConfigs = configService.getConfigs(proj.getId(), module.getModuleId(),
                            request.getOldConfigKey());
                    logger.info("ConfigEditRequest oldConfigs:{}", oldConfigs);
                    if ((null == oldConfigs) || oldConfigs.isEmpty()) {
                        response.setCode(ResponseCode.NOT_FOUND_OLD_CONFIG.getName());
                        response.setMsg(ResponseCode.NOT_FOUND_OLD_CONFIG.getDesc());
                        return response;
                    } else if (oldConfigs.size() > 1) {
                        response.setCode(ResponseCode.MUTIPLE_OLD_CONFIG.getName());
                        response.setMsg(ResponseCode.MUTIPLE_OLD_CONFIG.getDesc());
                        return response;
                    } else if (null != configs && configs.size() > 0) {
                        response.setCode(ResponseCode.MUTIPLE_CONFIG.getName());
                        response.setMsg(ResponseCode.MUTIPLE_CONFIG.getDesc());
                        return response;
                    } else {
                        configService.addNewAndDelOldConfig(request.getConfigKey(), request.getConfigValue(),
                                request.getConfigDesc(), proj.getId(), module.getModuleId(), user.getUserCode(),
                                oldConfigs.get(0).getConfigId());
                    }
                    break;
                case "DEL":
                    List<ConfProjectConfig> oldConfigList = configService.getConfigs(proj.getId(), module.getModuleId(),
                            request.getOldConfigKey());
                    logger.info("ConfigEditRequest oldConfigs:{}", oldConfigList);
                    if ((null == oldConfigList) || oldConfigList.isEmpty()) {
                        response.setCode(ResponseCode.NOT_FOUND_OLD_CONFIG.getName());
                        response.setMsg(ResponseCode.NOT_FOUND_OLD_CONFIG.getDesc());
                        return response;
                    } else if (oldConfigList.size() > 1) {
                        response.setCode(ResponseCode.MUTIPLE_OLD_CONFIG.getName());
                        response.setMsg(ResponseCode.MUTIPLE_OLD_CONFIG.getDesc());
                        return response;
                    } else {
                        configService.deleteConfig(oldConfigList.get(0).getConfigId(), proj.getId());
                    }
                    break;
                default:
                    response.setCode(ResponseCode.DO_NOTHING.getName());
                    response.setMsg(ResponseCode.DO_NOTHING.getDesc());
                    return response;
            }

        } catch (Throwable e) {
            logger.error("ConfigEditException{}", e.getMessage(), e);
            response.setCode(ResponseCode.FAIL.getName());
            response.setMsg(ResponseCode.FAIL.getDesc());
            return response;
        } finally {
            logger.info("ConfigEditResponse:{}", response);
        }
        return response;
    }

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("test");
        request.setConfigValue("test");
        request.setConfigDesc("test");
        request.setOldConfigKey(null);
        ObjectMapper mapper = new ObjectMapper();
        String Json = mapper.writeValueAsString(request);
        System.out.println(Json);
    }

}
