package com.cn.campus.mapper;

import com.cn.campus.entity.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 轮播图 Mapper 接口
 * </p>
 */
public interface BannerMapper extends BaseMapper<Banner> {

    List<Banner> pageQuery(Banner data);

}
