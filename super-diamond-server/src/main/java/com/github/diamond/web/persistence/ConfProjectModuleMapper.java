package com.github.diamond.web.persistence;

import com.github.diamond.web.model.ConfProjectModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConfProjectModuleMapper {

    int insert(ConfProjectModule record);

    int update(ConfProjectModule record);

    List<Map<String, Object>> queryModuleByProjId(@Param("projId")long projId);

    Long generatorId();

    void delByModuleAndProjId(@Param("moduleId")long moduleId,@Param("projId")long projId);

    int checkModuleNameExist(@Param("moduleName")String moduleName);
}