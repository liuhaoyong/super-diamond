package com.github.diamond.web.persistence;

import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.vo.ConfProjModuleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConfProjectConfigMapper {

    int insert(ConfProjectConfig record);

    ConfProjectConfig queryByPK(Integer configId);

    int update(ConfProjectConfig record);

    int checkProjConfExist(@Param("projectId")long projectId,@Param("moduleId")long moduleId);

    List<ConfProjModuleVO> queryConfigs(@Param("projectId")Long projectId,@Param("moduleId")Long moduleId,@Param("offset")int ofset,@Param("pageSize")int pageSize,@Param("confKey")String confKey);

    int queryConfigsCount(@Param("projectId")Long projectId,@Param("moduleId")Long moduleId,@Param("confKey")String confKey);

    List<Map<String, Object>> queryConfigsFor3Tbl(@Param("projectCode")String projectCode);

    Long generatorId();

    void updateDeleteFlag(@Param("deleteFlag")int deleteFlag,@Param("configId")long configId);

    List<ConfProjectConfig> queryConfigInfo(ConfProjectConfig record);

    ConfProjectConfig getAvailableConfig(@Param("configId")long configId);

    public List<Map<String,String>> getAllKeysByProjIdAndModuleId(@Param("projectId")Long projectId,@Param("moduleId")Long moduleId);
}