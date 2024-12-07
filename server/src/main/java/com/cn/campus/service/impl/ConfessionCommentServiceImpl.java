package com.cn.campus.service.impl;

import com.cn.campus.entity.ConfessionComment;
import com.cn.campus.mapper.ConfessionCommentMapper;
import com.cn.campus.service.ConfessionCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 论坛评论信息 服务实现类
 * </p>
 */
@Service
public class ConfessionCommentServiceImpl extends ServiceImpl<ConfessionCommentMapper, ConfessionComment> implements ConfessionCommentService {

    @Override
    public List<ConfessionComment> getComment(String id) {
        List<ConfessionComment> commentList =  baseMapper.getComment(id, "1", "");
        for (ConfessionComment comment : commentList) {
            comment.setChildComment(baseMapper.getComment(id, "2", comment.getId()));
        }
        return commentList;
    }
}
