package com.cn.campus.service;

import com.cn.campus.entity.Confession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论坛 服务类
 * </p>
 */
public interface ConfessionService extends IService<Confession> {

    /**
     * 查询论坛列表
     * @param userId
     * @param content
     * @return
     */
    List<Confession> getConfessionList(Long userId, String content);

    /**
     * 查询论坛详情
     * @return
     */
    Confession getConfessionById(String id);
}
