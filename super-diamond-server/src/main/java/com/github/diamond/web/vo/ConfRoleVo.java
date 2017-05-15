package com.github.diamond.web.vo;

/**
 * Created by Lenovo on 2015/5/4.
 */
public class ConfRoleVo {
    private Long projId;
    private Long userId;
    private String roleCode;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
