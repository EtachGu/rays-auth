package com.auth.entity;

import javax.persistence.*;

@Table(name = "users_roles")
public class UsersRoles {
    @Column(name = "user_name")
    private String userName;

    @Column(name = "role_id")
    private Long roleId;

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return role_id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}