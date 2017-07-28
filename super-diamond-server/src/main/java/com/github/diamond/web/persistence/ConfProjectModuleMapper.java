package com.github.diamond.web.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.diamond.web.model.ConfProjectModule;

public interface ConfProjectModuleMapper {

    int insert(ConfProjectModule record);

    int update(ConfProjectModule record);

    List<Map<String, Object>> queryModuleByProjId(@Param("projId") long projId);

    ConfProjectModule queryByProjIdAndModuleName(@Param("projId") long projId, @Param("moduleName") String moduleName);

    Long generatorId();

    void delByModuleAndProjId(@Param("moduleId") long moduleId, @Param("projId") long projId);

    int checkModuleNameExist(@Param("projId") Long projId, @Param("moduleName") String moduleName);
}
