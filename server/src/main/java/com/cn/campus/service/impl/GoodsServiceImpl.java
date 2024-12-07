package com.cn.campus.service.impl;

import com.cn.campus.entity.Goods;
import com.cn.campus.mapper.GoodsMapper;
import com.cn.campus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 二手市场 服务实现类
 * </p>
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public Goods getGoodsById(String id, String userId) {
        return baseMapper.getGoodsById(id, userId);
    }

    @Override
    public List<Goods> getGoods(String goodsType, String title) {
        return baseMapper.getGoods(goodsType, title);
    }
}
