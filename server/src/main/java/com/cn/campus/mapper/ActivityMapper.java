package com.cn.campus.mapper;

import com.cn.campus.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 校园活动表 Mapper 接口
 * </p>
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> pageQuery(Activity data);

    Activity getActivityById(@Param("id") String id,@Param("userId") Long userId);

}
