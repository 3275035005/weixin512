package com.cn.campus.mapper;

import com.cn.campus.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 意见反馈表 Mapper 接口
 * </p>
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    List<Feedback> pageQuery(Feedback data);
}
