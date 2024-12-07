package com.cn.campus.service;

import com.cn.campus.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.campus.utils.page.PageResult;

/**
 * <p>
 * 公告 服务类
 * </p>
 */
public interface NoticeService extends IService<Notice> {

    PageResult pageQuery(int page, int limit, Notice data);

}
