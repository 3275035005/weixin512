package com.cn.campus.service.impl;

import com.cn.campus.entity.Activity;
import com.cn.campus.entity.ActivityCollect;
import com.cn.campus.mapper.ActivityCollectMapper;
import com.cn.campus.service.ActivityCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 校园活动收藏 服务实现类
 * </p>
 */
@Service
public class ActivityCollectServiceImpl extends ServiceImpl<ActivityCollectMapper, ActivityCollect> implements ActivityCollectService {

    @Override
    public List<Activity> getActivityCollectByUserId(Long userId) {
        return baseMapper.getActivityCollectByUserId(userId);
    }
}
