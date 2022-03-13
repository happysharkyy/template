package com.ncu.building.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class SysRoleMenu implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Long roleId;

    private Long menuId;
}
