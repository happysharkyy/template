package com.ncu.building.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncu.building.common.api.ColumnFilter;
import com.ncu.building.common.api.PageRequest;
import com.ncu.building.common.api.PageResult;
import com.ncu.building.mapper.SysMenuMapper;
import com.ncu.building.mapper.SysRoleMapper;
import com.ncu.building.mapper.SysRoleMenuMapper;
import com.ncu.building.model.entity.SysMenu;
import com.ncu.building.model.entity.SysRole;
import com.ncu.building.model.entity.SysRoleMenu;
import com.ncu.building.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

;

/**
 * 角色 实现类
 *
 * @author Knox 2020/11/7
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Override
    public SysRole getRoleById(Integer roleId) {
        return this.baseMapper.selectById(roleId);
    }

    @Override
    public List<SysMenu> findRoleMenus(int roleId) {
        SysRole sysRole = baseMapper.selectById(roleId);
        if (sysRole.getName() == "admin") {
            return menuMapper.selectList(null);
        }
        List<SysRoleMenu> list = roleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        List<Long> ids = list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return menuMapper.selectBatchIds(ids);
    }

    @Override
    public void saveRoleMenus(List<SysRoleMenu> records) {
        if(records == null || records.isEmpty()) {
            return ;
        }
        Long roleId = records.get(0).getRoleId();
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        for(SysRoleMenu record:records) {
            roleMenuMapper.insert(record);
        }
    }
    @Override
    public void delete(List<SysRole> roles) {
        for (SysRole role : roles) {
            removeById(role.getId());
        }
    }

    @Override
    public PageResult findPage( PageRequest pageRequest) {
        ColumnFilter columnFilter = pageRequest.getColumnFilters().get("name");
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (columnFilter != null && !StringUtils.isEmpty(columnFilter.getValue())) {
            wrapper.eq(SysRole::getName, columnFilter.getValue());
        }
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        IPage<SysRole> result = baseMapper.selectPage(page, wrapper);
        PageResult pageResult = new PageResult(result);
        return pageResult;
    }
}
