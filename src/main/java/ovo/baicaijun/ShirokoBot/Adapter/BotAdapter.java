package ovo.baicaijun.ShirokoBot.Adapter;

import ovo.baicaijun.ShirokoBot.Bot.Message;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

/**
 * Bot 适配器接口
 * 定义所有协议适配器必须实现的方法
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public interface BotAdapter {
    
    /**
     * 初始化适配器
     */
    void initialize();
    
    /**
     * 启动适配器
     */
    void start();
    
    /**
     * 停止适配器
     */
    void stop();
    
    /**
     * 获取适配器名称
     */
    String getName();
    
    /**
     * 获取适配器版本
     */
    String getVersion();
    
    /**
     * 发送消息
     */
    CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message);
    
    /**
     * 发送私聊消息
     */
    CompletableFuture<Message> sendPrivateMessage(long userId, String message);
    
    /**
     * 发送群消息
     */
    CompletableFuture<Message> sendGroupMessage(long groupId, String message);
    
    /**
     * 撤回消息
     */
    CompletableFuture<Boolean> deleteMessage(long groupId, long userId,long messageId);
    
    /**
     * 发送点赞
     */
    CompletableFuture<Boolean> sendLike(long userId, int times);
    
    /**
     * 群组踢人
     */
    CompletableFuture<Boolean> setGroupKick(long groupId, long userId, boolean rejectAddRequest);
    
    /**
     * 群组禁言
     */
    CompletableFuture<Boolean> setGroupBan(long groupId, long userId, long duration);
    
    /**
     * 获取登录号信息
     */
    CompletableFuture<JSONObject> getLoginInfo();
    
    /**
     * 获取群信息
     */
    CompletableFuture<JSONObject> getGroupInfo(long groupId, boolean noCache);
    
    /**
     * 获取群成员信息
     */
    CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId, boolean noCache);
}
