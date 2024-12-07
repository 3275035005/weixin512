package com.cn.campus.mapper;

import com.cn.campus.entity.Activity;
import com.cn.campus.entity.ActivityCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 校园活动收藏 Mapper 接口
 * </p>
 */
public interface ActivityCollectMapper extends BaseMapper<ActivityCollect> {

    List<Activity> getActivityCollectByUserId(@Param("userId") Long userId);

}
