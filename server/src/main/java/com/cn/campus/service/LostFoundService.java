package com.cn.campus.service;

import com.cn.campus.entity.LostFound;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.campus.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 失物招领 服务类
 * </p>
 */
public interface LostFoundService extends IService<LostFound> {

    PageResult pageQuery(int page, int limit, LostFound data);

    List<LostFound> getLostFound(String type, String goodsType, String title);

    LostFound getLostFoundById(String id);

}
