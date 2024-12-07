package com.cn.campus.mapper;

import com.cn.campus.entity.Confession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 论坛 Mapper 接口
 * </p>
 */
public interface ConfessionMapper extends BaseMapper<Confession> {

    List<Confession> getConfessionList(@Param("userId") Long userId,@Param("content") String content);

    Confession getConfessionById(@Param("id")String id);

}
