package com.ncu.building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncu.building.common.api.PageRequest;
import com.ncu.building.common.api.PageResult;
import com.ncu.building.model.entity.SysDict;

import java.util.List;

public interface SysDictService extends IService<SysDict> {

    void delete(List<SysDict> records);

    PageResult findPage(PageRequest pageRequest);

    List<SysDict> findByLable(String lable);
}
