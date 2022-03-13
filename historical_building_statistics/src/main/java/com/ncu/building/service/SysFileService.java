package com.ncu.building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncu.building.model.entity.SysFile;

public interface SysFileService extends IService<SysFile>{
        int insert(SysFile sysFile);
}
