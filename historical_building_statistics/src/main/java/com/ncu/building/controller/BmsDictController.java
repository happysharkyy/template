package com.ncu.building.controller;

import com.ncu.building.common.api.ApiResult;
import com.ncu.building.common.api.PageRequest;
import com.ncu.building.model.entity.SysDict;
import com.ncu.building.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dict")
public class BmsDictController {

    @Autowired
    private SysDictService dictService;


    @PostMapping(value="/save")
    public ApiResult save(@RequestBody SysDict record) {
        return ApiResult.success(dictService.saveOrUpdate(record));
    }


    @PostMapping(value="/delete")
    public ApiResult delete(@RequestBody List<SysDict> records) {
        dictService.delete(records);
        return ApiResult.success(1);
    }


    @PostMapping(value="/findPage")
    public ApiResult findPage(@RequestBody PageRequest pageRequest) {
        return ApiResult.success(dictService.findPage(pageRequest));
    }


    @GetMapping(value="/findByLable")
    public ApiResult findByLable(@RequestParam String lable) {
        return ApiResult.success(dictService.findByLable(lable));
    }
}
