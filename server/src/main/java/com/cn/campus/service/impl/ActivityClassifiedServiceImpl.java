package com.cn.campus.service.impl;

import com.cn.campus.entity.ActivityClassified;
import com.cn.campus.mapper.ActivityClassifiedMapper;
import com.cn.campus.service.ActivityClassifiedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.campus.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class ActivityClassifiedServiceImpl extends ServiceImpl<ActivityClassifiedMapper, ActivityClassified> implements ActivityClassifiedService {

    @Override
    public PageResult pageQuery(int page, int limit, ActivityClassified data) {
        PageHelper.startPage(page, limit);
        List<ActivityClassified> queryList = baseMapper.pageQuery(data);
        PageInfo<ActivityClassified> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<ActivityClassified> getActivityClassifiedBySumNumber() {
        return baseMapper.getActivityClassifiedBySumNumber();
    }
}
