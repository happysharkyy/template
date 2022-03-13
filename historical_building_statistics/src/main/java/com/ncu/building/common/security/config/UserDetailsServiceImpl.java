package com.ncu.building.common.security.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ncu.building.mapper.SysPermissionMapper;
import com.ncu.building.model.entity.SysPermission;
import com.ncu.building.model.entity.SysUser;
import com.ncu.building.service.IUmsUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author knox
 * @since 2021-01-13
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUmsUserService userService;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取登录用户信息
        SysUser admin = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (admin != null) {
            // 获取用户可访问资源
            List<SysPermission> resourceList = sysPermissionMapper.selectList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
