package com.cn.campus.service;

import com.cn.campus.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 二手市场 服务类
 * </p>
 */
public interface GoodsService extends IService<Goods> {


    Goods getGoodsById(String id, String userId);

    List<Goods> getGoods(String goodsType, String title);
}
