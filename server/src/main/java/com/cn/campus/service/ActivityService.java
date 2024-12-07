package com.cn.campus.service;

import com.cn.campus.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.campus.utils.page.PageResult;

/**
 * <p>
 * 校园活动表 服务类
 * </p>
 */
public interface ActivityService extends IService<Activity> {

    PageResult pageQuery(int page, int limit, Activity data);

    /**
     * 查询活动详情和收藏id
     * @param id
     * @return
     */
    Activity getActivityById(String id, Long userId);
}
