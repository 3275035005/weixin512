package com.cn.campus.service;

import com.cn.campus.entity.Activity;
import com.cn.campus.entity.ActivityCollect;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 校园活动收藏 服务类
 * </p>
 */
public interface ActivityCollectService extends IService<ActivityCollect> {

    /**
     * 通过用户id 查询我的收藏
     * @param userId
     * @return
     */
    List<Activity> getActivityCollectByUserId(Long userId);
}
