package com.cn.campus.service.impl;

import com.cn.campus.entity.LostFound;
import com.cn.campus.mapper.LostFoundMapper;
import com.cn.campus.service.LostFoundService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.campus.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 失物招领和寻物启事 服务实现类
 * </p>
 */
@Service
public class LostFoundServiceImpl extends ServiceImpl<LostFoundMapper, LostFound> implements LostFoundService {

    @Override
    public PageResult pageQuery(int page, int limit, LostFound data) {
        PageHelper.startPage(page, limit);
        List<LostFound> queryList = baseMapper.pageQuery(data);
        PageInfo<LostFound> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<LostFound> getLostFound(String type, String goodsType, String title) {
        return baseMapper.getLostFound(type, goodsType, title);
    }

    @Override
    public LostFound getLostFoundById(String id) {
        return baseMapper.getLostFoundById(id);
    }
}
