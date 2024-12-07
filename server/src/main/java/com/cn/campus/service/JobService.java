package com.cn.campus.service;

import com.cn.campus.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 兼职信息 服务类
 * </p>
 */
public interface JobService extends IService<Job> {

    List<Job> getConfessionList(Long userId, String content);
    
}
