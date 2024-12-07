package com.cn.campus.service.impl;

import com.cn.campus.entity.Confession;
import com.cn.campus.mapper.ConfessionMapper;
import com.cn.campus.service.ConfessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 论坛 服务实现类
 * </p>
 */
@Service
public class ConfessionServiceImpl extends ServiceImpl<ConfessionMapper, Confession> implements ConfessionService {

    @Override
    public List<Confession> getConfessionList(Long userId, String content) {
        return baseMapper.getConfessionList(userId, content);
    }

    @Override
    public Confession getConfessionById(String id) {
        return baseMapper.getConfessionById(id);
    }
}
