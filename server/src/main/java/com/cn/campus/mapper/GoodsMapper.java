package com.cn.campus.mapper;

import com.cn.campus.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 二手市场 Mapper 接口
 * </p>
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    Goods getGoodsById(@Param("id") String id, @Param("userId") String userId);

    List<Goods> getGoods( @Param("goodsType") String goodsType,
                          @Param("title") String title);
}
