package com.github.diamond.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.diamond.web.common.SuperDiamondConstant;
import com.github.diamond.web.model.ConfProject;
import com.github.diamond.web.model.ConfUser;
import com.github.diamond.web.persistence.ConfProjectMapper;
import com.github.diamond.web.persistence.ConfUserMapper;
import com.github.diamond.web.strategy.Context;
import com.github.diamond.web.vo.ConfRoleVo;
import com.github.diamond.web.vo.ConfUserProjectVO;

/**
 * Create on @2013-7-18 @下午10:51:27
 * 
 * @author melin
 */
@Service
public class ProjectService {
    @Autowired
    private ConfProjectMapper confProjectMapper;
    @Autowired
    private ConfUserMapper    confUserMapper;

    Logger                    logger = LoggerFactory.getLogger(this.getClass());

    public List<ConfUserProjectVO> queryProjects(ConfUser user) {
        logger.info("begin - ProjectService.queryProjects method");
        Long ownerId = null;
        /** 如果是管理员，可以看到所有人的项目 **/
        if (!SuperDiamondConstant.ADMIN.equals(user.getUserCode())) {
            ownerId = user.getId();
        }
        return this.confProjectMapper.queryProjectsByOwner(ownerId);
    }

    /**
     * 根据userCode查询userId
     * 
     * @param userCode
     * @return
     */
    public long findUserId(String userCode) {
        try {
            ConfUser user = this.confUserMapper.queryUserByUserCode(userCode);
            if (user == null) {
                return 0;
            }
            return user.getId();
        } catch (DataAccessException e) {
            return 0;
        }
    }

    /**
     * 保存project
     * 
     * @param project
     */
    @Transactional
    public void saveProject(ConfProject project) {
        long id = 1;
        Long genId = this.confProjectMapper.generatorId();
        if (genId != null) {
            id = genId.longValue();
        }
        project.setId(id);
        project.setCreateTime(new Date());
        this.confProjectMapper.insert(project);
        this.saveUser(id, project.getOwnerId(), SuperDiamondConstant.ENV_DEVELOPMENT, SuperDiamondConstant.ENV_TEST,
                SuperDiamondConstant.ENV_PRODUCTION, SuperDiamondConstant.ENV_PRE, SuperDiamondConstant.ADMIN);
    }

    /**
     * 删除项目
     * 
     * @param id
     */
    @Transactional
    public void deleteProject(long id) {
        logger.info("begin - ProjectService.deleteProject,id=" + id + "");
        ConfProject project = new ConfProject();
        project.setId(id);
        project.setDeleteFlag(SuperDiamondConstant.STATUS_DEL);
        this.confProjectMapper.update(project);
    }

    public List<ConfUser> queryUsers(long projectId) {
        return this.confUserMapper.queryUsersByProjId(projectId);
    }

    public List<ConfUser> queryProjUsers(long projectId) {
        List<ConfUser> users = this.confUserMapper.queryProjUsers(projectId);
        for (ConfUser user : users) {
            List<ConfRoleVo> roleVos = this.confUserMapper.queryRoles(projectId, user.getId());
            user.setRoles(roleVos);
        }
        return users;
    }

    public List<ConfRoleVo> queryRoles(long projectId, long userId) {
        return this.confUserMapper.queryRoles(projectId, userId);
    }

    //TODO
    @Transactional
    public void saveUser(long projectId, long userId, String development, String test, String production, String pre,
                         String admin) {
        this.confUserMapper.insertPrjUsr(projectId, userId);
        List<String> roleList = new ArrayList<String>();
        if (StringUtils.isNotBlank(admin)) {
            roleList.add(SuperDiamondConstant.ADMIN);
            //如果拥有admin权限，自动添加development、test、production,staging
            roleList.add(SuperDiamondConstant.ENV_DEVELOPMENT);
            roleList.add(SuperDiamondConstant.ENV_TEST);
            roleList.add(SuperDiamondConstant.ENV_PRODUCTION);
            roleList.add(SuperDiamondConstant.ENV_PRE);
        } else {
            roleList.add(development);
            roleList.add(test);
            roleList.add(production);
            roleList.add(pre);
        }
        for (String role : roleList) {
            if (StringUtils.isNotBlank(role)) {
                this.confUserMapper.insertConfProjUserRole(projectId, userId, role);
            }
        }
    }

    @Transactional
    public void deleteUser(long projectId, long userId) {
        this.confUserMapper.delProjectUserRole(projectId, userId);
        this.confUserMapper.delProjectUser(projectId, userId);
    }

    /**
     * 查询用户所拥有的项目
     * 
     * @param user
     */
    public List<ConfProject> queryProjectForUser(ConfUser user) {
        Long userId = null;
        if (!"admin".equals(user.getUserCode())) {
            userId = user.getId();
        }
        List<ConfProject> projects = this.confProjectMapper.queryProjectForUser(userId);
        return projects;
    }

    /**
     * 增加配置项时，增加版本号
     * 
     * @param projectId
     */
    @Transactional
    public void updateVersion(Long projectId) {
        this.confProjectMapper.updateVersion(projectId);
    }

    /**
     * 增加配置项时，增加版本号
     * 
     * @param projectId
     */
    @Transactional
    public void updateVersion(Long projectId, String type) {
        Context context = new Context(SuperDiamondConstant.envMap.get(type));
        String upField = context.getModifyVersion();
        this.confProjectMapper.dynaModifyVersion(upField, projectId);
    }

    public Map<String, Object> queryProject(Long projectId) {
        return this.confProjectMapper.queryByPK(projectId);
    }

    public ConfProject queryByProjCode(String projCode) {
        return this.confProjectMapper.queryByProjCode(projCode);
    }
}
