package com.ncu.building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncu.building.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}
