package com.ncu.building.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncu.building.common.api.ApiResult;
import com.ncu.building.common.api.PageRequest;
import com.ncu.building.model.dto.LoginDTO;
import com.ncu.building.model.dto.RegisterDTO;
import com.ncu.building.model.entity.SysUser;
import com.ncu.building.service.IUmsUserService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.ncu.building.model.vo.SysUserVO;


@RestController
@RequestMapping("/ums/user")
public class SysUserController extends BaseController {
    @Resource
    private IUmsUserService iUmsUserService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        SysUser user = iUmsUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        System.out.println(dto.toString());
        String token = iUmsUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        map.put("avatar", iUmsUserService.getUserByUsername(dto.getUsername()).getAvatar());
        return ApiResult.success(map, "登录成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<SysUser> getUser(Principal principal) {
        SysUser user = iUmsUserService.getUserByUsername(principal.getName());
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    @GetMapping("/getByUserName/{username}")
    public ApiResult getUserByName01(@PathVariable("username") String username) {
        SysUser user = iUmsUserService.getUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        return ApiResult.success(user);
    }

    @GetMapping("/Export")
    public ResponseEntity<byte[]> getUserByName(HttpServletResponse response) throws IOException {
        List<SysUser> list = iUmsUserService.list();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (26.25 * 20));
        row.createCell(0).setCellValue("用户信息列表");//为第一行单元格设值


        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);

      /*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
      sheet.addMergedRegion(columnRegion);*/

        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("Id");//为第一个单元格设值
        row.createCell(1).setCellValue("昵称");//为第二个单元格设值
        row.createCell(2).setCellValue("头像");//为第三个单元格设值
        row.createCell(3).setCellValue("邮箱");//为第四个单元格设值
        row.createCell(4).setCellValue("手机号");//为第四个单元格设值
        row.createCell(5).setCellValue("用户名");//为第四个单元格设值
        row.createCell(6).setCellValue("职业");//为第四个单元格设值
        row.createCell(7).setCellValue("创建时间");//为第四个单元格设值
        row.createCell(8).setCellValue("是否激活");//为第四个单元格设值、
        row.createCell(9).setCellValue("积分");//为第四个单元格设值


        //遍历所获取的数据
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            SysUser bgm = list.get(i);
            row.createCell(0).setCellValue(bgm.getId());
            row.createCell(1).setCellValue(bgm.getAlias());
            row.createCell(2).setCellValue(bgm.getAvatar());
            row.createCell(3).setCellValue(bgm.getEmail());
            row.createCell(4).setCellValue(bgm.getMobile());
            row.createCell(5).setCellValue(bgm.getUsername());
            row.createCell(6).setCellValue(bgm.getBio());
            row.createCell(7).setCellValue(bgm.getCreateTime());
            row.createCell(8).setCellValue(bgm.getStatus());
            row.createCell(9).setCellValue(bgm.getScore());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            wb.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("导出成功！");
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
    @PostMapping(value="/update")
    public ApiResult<SysUser> updateUser(@RequestBody SysUser sysUser) {

//        SysUser sysUser = new SysUser();
//        BeanUtils.copyProperties(sysUserVO,sysUser);
//        sysUser.setModifyTime(new Date());
//        sysUser.setUserRoles(sysUserVO.getUserRoleid());
//
        System.out.println(sysUser+"-----------------------------------------------------------");
        iUmsUserService.updateById(sysUser);
        return ApiResult.success(sysUser);
    }
    //    @PreAuthorize("hasAuthority('user:view')")
//    @RequiresPermissions("sys:user:view")
//    @GetMapping(value="/findUserRoles")
//    public ApiResult findUserRoles(@RequestParam Long userId) {
//        return ApiResult.success(iUmsUserService.findUserRoles(userId));
//    }

//    @Log("查看用户")
//    @RequiresPermissions("sys:user:view")
    @PostMapping(value="/findPage")
    public ApiResult findPage(@RequestBody PageRequest pageRequest) {
        System.out.println(iUmsUserService.findPage(pageRequest));
        return ApiResult.success(iUmsUserService.findPage(pageRequest));
    }
    @GetMapping(value="/findPermissions")
    public ApiResult findPermissions(@RequestParam String name) {
        return ApiResult.success(iUmsUserService.findPermissions(name));
    }

    @PostMapping(value="/save")
    public ApiResult save(@RequestBody SysUser record) {
        System.out.println(record+"-----------------------01");
        SysUser user = iUmsUserService.getUserByUsername(record.getUsername());
        if(user != null) {
            if("admin".equalsIgnoreCase(user.getUsername())) {
                return ApiResult.failed("超级管理员不允许修改!");
            }
        }
        if(record.getPassword() != null) {
            if(user == null) {
                // 新增用户
                if(iUmsUserService.getUserByUsername(record.getUsername()) != null) {
                    return ApiResult.failed("用户名已存在!");
                }
                record.setPassword(passwordEncoder.encode(record.getPassword()));
            } else {
                // 修改用户, 且修改了密码
                if(!record.getPassword().equals(user.getPassword())) {
                    System.out.println("-------------"+passwordEncoder.encode(record.getPassword()));
                    record.setPassword(passwordEncoder.encode(record.getPassword()));
                }
            }
        }
        record.setModifyTime(new Date());

        return iUmsUserService.saveUser(record);
    }


    @PostMapping(value="/delete")
    public ApiResult delete(@RequestBody List<SysUser> records) {
        for(SysUser record : records) {
            SysUser sysUser = iUmsUserService.getUserByUsername(record.getUsername());
            if(sysUser != null && "admin".equalsIgnoreCase(sysUser.getUsername())) {
                return ApiResult.failed("超级管理员不允许删除!");
            }
        }
        return ApiResult.success("成功");
    }
    @GetMapping(value="/getTodayAddUser/list")
    public ApiResult getTodayAddUser() {
        return ApiResult.success(iUmsUserService.getTodayAddUser());
    }
}
