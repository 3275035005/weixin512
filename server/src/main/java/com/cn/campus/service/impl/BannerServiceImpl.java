package com.cn.campus.service.impl;

import com.cn.campus.entity.Banner;
import com.cn.campus.mapper.BannerMapper;
import com.cn.campus.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.campus.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public PageResult pageQuery(int page, int limit, Banner data) {
        PageHelper.startPage(page, limit);
        List<Banner> queryList = baseMapper.pageQuery(data);
        PageInfo<Banner> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
