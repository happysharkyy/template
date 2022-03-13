package com.ncu.building.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncu.building.common.api.ApiResult;
import com.ncu.building.common.api.PageRequest;
import com.ncu.building.common.api.PageResult;
import com.ncu.building.model.dto.LoginDTO;
import com.ncu.building.model.dto.RegisterDTO;
import com.ncu.building.model.entity.SysUser;
import com.ncu.building.model.vo.ProfileVO;

import java.util.List;
import java.util.Set;


public interface IUmsUserService extends IService<SysUser> {

    int delete(List<SysUser> users);
    /**
     * 注册功能
     *
     * @param dto
     * @return 注册对象
     */
    SysUser executeRegister(RegisterDTO dto);
    /**
     * 获取用户信息
     *
     * @param username
     * @return dbUser
     */
    SysUser getUserByUsername(String username);
    /**
     * 用户登录
     *
     * @param dto
     * @return 生成的JWT的token
     */
    String executeLogin(LoginDTO dto);
    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);


    PageResult findPage(PageRequest pageRequest);
    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);

    ApiResult saveUser(SysUser record);

    int getTodayAddUser();
//    List<SysUserRole> findUserRoles(Long userId);
}
