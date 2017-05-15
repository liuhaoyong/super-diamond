package com.github.diamond.web.persistence;

import com.github.diamond.web.model.ConfUser;
import com.github.diamond.web.vo.ConfRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfUserMapper{

    /**
     * 根据userCode查询用户信息
     * @param userCode
     * @return
     */
    ConfUser queryUserByUserCode(@Param("userCode")String userCode);

    /**
     * 根据主键得到用户
     * @param id
     * @return
     */
    ConfUser queryByPK(@Param("id")Integer id);

    /**
     * 查询所有用户
     * @return
     */
    List<ConfUser> queryUserList();

    /**
     * 保存用户
     * @param confUser
     */
    void insert(ConfUser confUser);

    /**
     * 修改用户
     * @param confUser
     */
    void update(ConfUser confUser);

    /**
     * 生成唯一主键
     * @return
     */
    Long generatorId();

    /**
     * 查询roleCode
     * @return
     */
    List<ConfRoleVo> queryRoles(@Param("projectId")long projectId,@Param("userId")long userId);

    /**
     * 添加项目与用户的关联关系
     * @param projId
     * @param userId
     */
    void insertPrjUsr(@Param("projId")long projId,@Param("userId")long userId);

    /**
     * 删除项目与角色的关联关系
     * @param projId
     * @param userId
     */
    void delProjectUserRole(@Param("projId")long projId,@Param("userId")long userId);

    /**
     * 删除用户与项目的关联关系
     */
    void delProjectUser(@Param("projId")long projId,@Param("userId")long userId);

    /**
     * 根据项目id查询用户列表
     * @param projectId
     * @return
     */
    List<ConfUser> queryUsersByProjId(@Param("projectId")long projectId);

    List<ConfUser> queryProjUsers(@Param("projId")long projId);

    /**
     * 保存项目和用户之间的关联关系
     * @param projectId
     * @param userId
     * @param roleCode
     */
    void insertConfProjUserRole(@Param("projectId")long projectId,@Param("userId")long userId,@Param("roleCode")String roleCode);
}