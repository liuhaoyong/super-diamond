package com.github.diamond.test.facade.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.diamond.facade.enums.ResponseCode;
import com.github.diamond.facade.request.ConfigEditRequest;
import com.github.diamond.facade.response.ConfigEditResponse;
import com.github.diamond.web.model.ConfProject;
import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.model.ConfProjectModule;
import com.github.diamond.web.service.ConfigService;
import com.github.diamond.web.service.ModuleService;
import com.github.diamond.web.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:META-INF/spring/application-common.xml",
        "classpath:META-INF/spring/config-servlet.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ConfigFacadeImplTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc               mockMvc;

    private static ObjectMapper   mapper = new ObjectMapper();

    @Autowired
    private ProjectService        projectService;
    @Autowired
    private ModuleService         moduleService;
    @Autowired
    private ConfigService         configService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public ConfigEditResponse postEditService(ConfigEditRequest request) {
        String requestJson;
        try {
            requestJson = mapper.writeValueAsString(request);
            String responseString = mockMvc
                    .perform(post("/tuhu_configuration_center/edit").content(requestJson)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            ConfigEditResponse response = mapper.readValue(responseString, ConfigEditResponse.class);
            return response;
        } catch (Throwable e) {
            System.out.println(e);
        }
        return null;
    }

    public ConfProjectConfig getDbConf(String projCode, String moduleName, String configKey) {
        ConfProject proj = projectService.queryByProjCode(projCode);
        ConfProjectModule module = moduleService.queryModules(proj.getId(), moduleName);
        List<ConfProjectConfig> configs = configService.getConfigs(proj.getId(), module.getModuleId(), configKey);
        if (null != configs && configs.size() > 0) {
            return configs.get(0);
        } else {
            return null;
        }
    }

    public void assertConf(ConfigEditRequest request, ConfProjectConfig config) {
        Assert.assertEquals(request.getConfigKey(), config.getConfigKey());
        Assert.assertEquals(request.getConfigValue(), config.getConfigValue());
        Assert.assertEquals(request.getConfigDesc(), config.getConfigDesc());
        Assert.assertEquals(request.getConfigDesc(), config.getConfigDesc());
    }

    @Test
    public void testInvalidUser() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("invalid");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_USER.getName(), response.getCode());
    }

    @Test
    public void testInvalidPassword() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("invalid");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.ERROR_PASSWORD.getName(), response.getCode());
    }

    @Test
    public void testInvalidProj() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("invalid");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_PROJECT.getName(), response.getCode());
    }

    @Test
    public void testInvalidModule() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("invalid");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_MODULE.getName(), response.getCode());
    }

    @Test
    public void testAdd() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey(null);
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.SUCCESS.getName(), response.getCode());
        ConfProjectConfig dbConf = getDbConf(request.getProjCode(), request.getModuleName(), request.getConfigKey());
        assertConf(request, dbConf);
    }

    @Test
    public void testRepeatAdd() throws Exception {
        testAdd();
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey(null);
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.CANNOT_ADD_SAME_CONFIG.getName(), response.getCode());
    }

    @Test
    public void testUpdate() throws Exception {
        testAdd();
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.SUCCESS.getName(), response.getCode());

        ConfProjectConfig dbConf = getDbConf(request.getProjCode(), request.getModuleName(), request.getConfigKey());
        assertConf(request, dbConf);
    }

    @Test
    public void testUpdateNotHaveConfig() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_CONFIG.getName(), response.getCode());
    }

    @Test
    public void testDelAndAdd() throws Exception {
        testAdd();
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testNewKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.SUCCESS.getName(), response.getCode());

        ConfProjectConfig dbConf = getDbConf(request.getProjCode(), request.getModuleName(), request.getConfigKey());
        assertConf(request, dbConf);

        ConfProjectConfig oldDbConf = getDbConf(request.getProjCode(), request.getModuleName(),
                request.getOldConfigKey());
        Assert.assertNull(oldDbConf);
    }

    @Test
    public void testDelAndAddNotHaveOldConfig() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testNewKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_OLD_CONFIG.getName(), response.getCode());
    }

    @Test
    public void testDelAndAddAlreadyHaveConfig() throws Exception {
        testAdd();
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testNewKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey(null);
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.SUCCESS.getName(), response.getCode());

        request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey("testNewKey");
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        response = postEditService(request);
        Assert.assertEquals(ResponseCode.MUTIPLE_CONFIG.getName(), response.getCode());
    }

    @Test
    public void testDel() throws Exception {
        testAdd();
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey(null);
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.SUCCESS.getName(), response.getCode());

        ConfProjectConfig oldDbConf = getDbConf(request.getProjCode(), request.getModuleName(),
                request.getOldConfigKey());
        Assert.assertNull(oldDbConf);
    }

    @Test
    public void testDelNotHaveOldConfig() throws Exception {
        ConfigEditRequest request = new ConfigEditRequest();
        request = new ConfigEditRequest();
        request.setUserCode("quyinjun");
        request.setMd5Passwd("25d55ad283aa400af464c76d713c07ad");
        request.setProjCode("cashier-core");
        request.setModuleName("dimensionDef");
        request.setConfigKey(null);
        request.setConfigValue("testValue");
        request.setConfigDesc("testDesc");
        request.setOldConfigKey("testKey");
        ConfigEditResponse response = postEditService(request);
        Assert.assertEquals(ResponseCode.NOT_FOUND_OLD_CONFIG.getName(), response.getCode());
    }
}
