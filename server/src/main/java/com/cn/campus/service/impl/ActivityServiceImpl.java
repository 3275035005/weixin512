package com.cn.campus.service.impl;

import com.cn.campus.entity.Activity;
import com.cn.campus.mapper.ActivityMapper;
import com.cn.campus.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.campus.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 校园活动表 服务实现类
 * </p>
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Override
    public PageResult pageQuery(int page, int limit, Activity data) {
        PageHelper.startPage(page, limit);
        List<Activity> queryList = baseMapper.pageQuery(data);
        PageInfo<Activity> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public Activity getActivityById(String id, Long userId) {
        Activity activity = baseMapper.getActivityById(id, userId);
        Integer seat = 0;
        if(activity.getUserList() != null){
            seat = activity.getUserList().size();
        }

        activity.setSeat(seat);
        return activity;
    }
}
