package com.github.diamond.web.vo;

/**
 * Created by qingyin on 2015/5/4.
 */
public class ConfUserProjectVO {
    private Long id;
    private String projCode;
    private String projName;
    private String userName;
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "ConfUserProjectVO{" +
                "id=" + id +
                ", projCode='" + projCode + '\'' +
                ", projName='" + projName + '\'' +
                ", userName='" + userName + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
