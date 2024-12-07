package com.cn.campus.mapper;

import com.cn.campus.entity.ConfessionComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 论坛评论信息 Mapper 接口
 * </p>
 */
public interface ConfessionCommentMapper extends BaseMapper<ConfessionComment> {

    List<ConfessionComment> getComment(@Param("confessionId")String confessionId,
                                       @Param("type") String type,
                                       @Param("parentId") String parentId);

}
