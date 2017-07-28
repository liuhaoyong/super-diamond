package com.github.diamond.web.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.diamond.web.model.ConfProject;
import com.github.diamond.web.vo.ConfUserProjectVO;

public interface ConfProjectMapper {

    int insert(ConfProject record);

    int update(ConfProject record);

    Map<String, Object> queryByPK(Long id);

    ConfProject queryByProjCode(String projCode);

    List<ConfUserProjectVO> queryProjectsByOwner(@Param("ownerId") Long ownerId);

    Long generatorId();

    List<ConfProject> queryProjectForUser(@Param("userId") Long userId);

    void updateVersion(@Param("id") long id);

    void dynaModifyVersion(@Param("modifyField") String modifyField, @Param("id") long id);
}
