package com.cn.campus.service.impl;

import com.cn.campus.entity.Job;
import com.cn.campus.mapper.JobMapper;
import com.cn.campus.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 兼职信息 服务实现类
 * </p>
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Override
    public List<Job> getConfessionList(Long userId, String content) {
        return baseMapper.getConfessionList(userId, content);
    }
}
