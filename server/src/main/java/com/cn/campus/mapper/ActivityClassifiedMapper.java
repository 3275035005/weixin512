package com.cn.campus.mapper;

import com.cn.campus.entity.ActivityClassified;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface ActivityClassifiedMapper extends BaseMapper<ActivityClassified> {

    List<ActivityClassified> pageQuery(ActivityClassified data);

    List<ActivityClassified> getActivityClassifiedBySumNumber();

}
