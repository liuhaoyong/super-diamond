package com.github.diamond.web.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Service;

import com.github.diamond.utils.MD5;
import com.github.diamond.web.common.SuperDiamondEnumCode;
import com.github.diamond.web.model.ConfUser;
import com.github.diamond.web.persistence.ConfUserMapper;

@Service
public class UserService {

    @Autowired
    private ConfUserMapper      confUserMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 用户登录方法
     * 
     * @param userCode
     * @param password
     * @return
     */
    public Object login(String userCode, String password) {
        logger.info("begin - login method,userCode=" + userCode + "");
        String md5Passwd = MD5.getInstance().getMD5String(password);
        try {
            ConfUser user = confUserMapper.queryUserByUserCode(userCode);
            if (user.getDeleteFlag() == 1) {
                return SuperDiamondEnumCode.USER_CANCEL.getMsg();
            }
            if (md5Passwd.equals(user.getPassword())) {
                user.setUserCode(userCode);
                return user;
            } else {
                return SuperDiamondEnumCode.USER_PWD_INCORRECT.getMsg();
            }
        } catch (TransientDataAccessResourceException e) {
            return SuperDiamondEnumCode.USER_NOT_EXISTS.getMsg();
        }
    }

    /**
     * 按用户名与md5Passwd来获取当前用户
     * 
     * @param userCode
     * @param password
     * @return
     */
    public Object fetch(String userCode, String md5Passwd) {
        logger.info("begin - fetch method,userCode=" + userCode + "");
        ConfUser user = confUserMapper.queryUserByUserCode(userCode);
        if (null == user) {
            return null;
        }
        if (user.getDeleteFlag() == 1) {
            return null;
        }
        return user;
    }

    /**
     * 查询所有有效用户
     * 
     * @return
     */
    public List<ConfUser> queryUsers() {
        logger.info("begin - queryUsers");
        return this.confUserMapper.queryUserList();
    }

    /**
     * 保存用户
     * 
     * @param user
     */
    public void saveUser(ConfUser user) {
        logger.info("begin - saveUser，user=" + user);
        long id = 1;
        Long genId = this.confUserMapper.generatorId();
        if (genId != null) {
            id = genId.longValue();
        }
        user.setId(id);
        user.setCreateTime(new Date());
        this.confUserMapper.insert(user);
        logger.info("end - saveUser");
    }

    /**
     * 删除用户信息
     * 
     * @param id
     */
    public void deleteUser(long id) {
        logger.info("begin - deleteUser");
        ConfUser user = new ConfUser();
        user.setDeleteFlag(1);
        user.setId(id);
        this.confUserMapper.update(user);
        logger.info("end - deleteUser");
    }

    /**
     * 修改密码
     * 
     * @param id
     * @param password
     */
    public void updatePassword(long id, String password) {
        logger.info("begin - updatePassword,id=" + id + "");
        ConfUser user = new ConfUser();
        user.setPassword(password);
        user.setId(id);
        user.setUpdateTime(new Date());
        this.confUserMapper.update(user);
        logger.info("end - updatePassword");
    }
}
