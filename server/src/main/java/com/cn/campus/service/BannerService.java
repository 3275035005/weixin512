package com.cn.campus.service;

import com.cn.campus.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.campus.utils.page.PageResult;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 */
public interface BannerService extends IService<Banner> {

    PageResult pageQuery(int page, int limit, Banner data);

}
