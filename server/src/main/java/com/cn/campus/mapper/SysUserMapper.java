package com.cn.campus.mapper;

import com.cn.campus.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> pageQuery(SysUser data);
}
