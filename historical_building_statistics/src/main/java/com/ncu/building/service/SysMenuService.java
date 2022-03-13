package com.ncu.building.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncu.building.model.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> findByUser(String userName, Wrapper<SysMenu> queryWrapper);

    List<SysMenu> findTree(String userName, int menuType, String name);

}
