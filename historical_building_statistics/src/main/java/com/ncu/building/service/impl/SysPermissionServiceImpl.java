package com.ncu.building.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncu.building.mapper.SysPermissionMapper;
import com.ncu.building.model.entity.SysPermission;
import com.ncu.building.service.SysPermissionService;
import com.ncu.building.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限 实现类
 *
 * @author Knox 2020/11/7
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getByRoleId(String userId) {
        return sysPermissionMapper.selectList(userId);
    }
}
