package com.cn.campus.service.impl;

import com.cn.campus.entity.UserChat;
import com.cn.campus.mapper.UserChatMapper;
import com.cn.campus.service.UserChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 聊天记录表 服务实现类
 * </p>
 */
@Service
public class UserChatServiceImpl extends ServiceImpl<UserChatMapper, UserChat> implements UserChatService {

    @Override
    public List<UserChat> getUserChatList(Long userId) {
        return baseMapper.getUserChatList(userId);
    }

    @Override
    public List<UserChat> getUserChatBySendUserAndReceiveUser(Long sendUserId, Long receiveUserId) {
        return baseMapper.getUserChatBySendUserAndReceiveUser(sendUserId, receiveUserId);
    }
}
