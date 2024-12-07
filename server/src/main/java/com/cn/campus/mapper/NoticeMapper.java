package com.cn.campus.mapper;

import com.cn.campus.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 公告 Mapper 接口
 * </p>
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> pageQuery(Notice data);


}
