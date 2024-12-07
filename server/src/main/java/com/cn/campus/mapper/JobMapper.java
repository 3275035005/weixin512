package com.cn.campus.mapper;

import com.cn.campus.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兼职信息 Mapper 接口
 * </p>
 */
public interface JobMapper extends BaseMapper<Job> {

    List<Job> getConfessionList(@Param("userId") Long userId, @Param("content") String content);

}
