package com.cn.campus.service.impl;

import com.cn.campus.entity.Feedback;
import com.cn.campus.mapper.FeedbackMapper;
import com.cn.campus.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.campus.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 意见反馈表 服务实现类
 * </p>
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public PageResult pageQuery(int page, int limit, Feedback data) {
        PageHelper.startPage(page, limit);
        List<Feedback> queryList = baseMapper.pageQuery(data);
        PageInfo<Feedback> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
