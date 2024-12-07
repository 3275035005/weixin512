package com.cn.campus.service;

import com.cn.campus.entity.ActivityClassified;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.campus.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ActivityClassifiedService extends IService<ActivityClassified> {

    PageResult pageQuery(int page, int limit, ActivityClassified data);

    /**
     * 统计校园活动分类的校园活动数量
     * @return
     */
    List<ActivityClassified> getActivityClassifiedBySumNumber();

}
