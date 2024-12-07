package com.cn.campus.service;

import com.cn.campus.entity.UserChat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 聊天记录表 服务类
 * </p>
 */
public interface UserChatService extends IService<UserChat> {

    /**
     * 我的聊天记录查询
     * @param userId
     * @return
     */
    List<UserChat> getUserChatList(Long userId);

    /**
     * 查询聊天窗口聊天记录
     * @param sendUserId
     * @param receiveUserId
     * @return
     */
    List<UserChat> getUserChatBySendUserAndReceiveUser(Long sendUserId, Long receiveUserId);
}
