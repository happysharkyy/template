package com.ncu.building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncu.building.model.entity.SysPermission;
import com.ncu.building.model.entity.SysRolePermission;

import java.util.List;

/**
 * 角色-权限接口
 *
 * @author Knox 2020/11/7
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 查询角色的权限关联记录
     *
     * @param roleId
     * @return
     */
    List<SysPermission> findPermissionByRoleId(Integer roleId);
    List<SysRolePermission> selectByRoleId(Integer roleId);
    List<SysPermission> queryAccessByRole();
}
