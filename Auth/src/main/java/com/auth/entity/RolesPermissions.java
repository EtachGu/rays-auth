package com.auth.entity;

import javax.persistence.*;

@Table(name = "roles_permissions")
public class RolesPermissions {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private String permissionId;

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

    /**
     * @return permission_id
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * @param permissionId
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}