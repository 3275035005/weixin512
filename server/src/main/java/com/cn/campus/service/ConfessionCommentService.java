package com.cn.campus.service;

import com.cn.campus.entity.ConfessionComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论坛评论信息 服务类
 * </p>
 */
public interface ConfessionCommentService extends IService<ConfessionComment> {

    /**
     * 通过论坛id查询帖子内容
     * @param id
     * @return
     */
    List<ConfessionComment> getComment(String id);

}
