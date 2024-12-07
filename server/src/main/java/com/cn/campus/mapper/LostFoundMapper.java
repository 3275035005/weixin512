package com.cn.campus.mapper;

import com.cn.campus.entity.LostFound;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 失物招领 Mapper 接口
 * </p>
 */
public interface LostFoundMapper extends BaseMapper<LostFound> {

    List<LostFound> pageQuery(LostFound data);

    List<LostFound> getLostFound(@Param("type") String type,
                                 @Param("goodsType") String goodsType,
                                 @Param("title") String title);

    LostFound getLostFoundById(@Param("id")String id);
}
