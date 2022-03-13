package com.ncu.building.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncu.building.mapper.SysFileMapper;
import com.ncu.building.model.entity.SysFile;
import com.ncu.building.service.SysFileService;
import org.springframework.stereotype.Service;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Override
    public int insert(SysFile sysFile) {
        return this.baseMapper.insert(sysFile);
    }
}
