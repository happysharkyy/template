package com.ncu.building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncu.building.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
