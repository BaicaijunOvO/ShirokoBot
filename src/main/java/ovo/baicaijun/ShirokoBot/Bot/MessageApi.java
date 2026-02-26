package ovo.baicaijun.ShirokoBot.Bot;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Log.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * 消息API
 * 通过适配器管理器统一调用不同协议的API
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class MessageApi {
    
    /**
     * 发送消息（自动判断群聊/私聊）- 异步返回
     */
    public static CompletableFuture<Message> send_msg(long group_id, long user_id, long bot_id, String msg) {
        String messageType = (group_id == 0) ? "private" : "group";
        Logger.info("Bot [" + bot_id + "] 正在发送消息到 ["+ group_id +"] ["+ user_id +"]: " + msg);
        
        return AdapterManager.sendMessage(messageType, group_id > 0 ? group_id : null, user_id, msg)
            .whenComplete((message, throwable) -> {
                if (throwable != null) {
                    Logger.error("Bot [" + bot_id + "] 消息发送异常: " + throwable.getMessage());
                } else if (message.isSuccess()) {
                    Logger.info("Bot [" + bot_id + "] seed a message to ["+ group_id +"] ["+ user_id +"]: " + msg);
                } else {
                    Logger.warn("Bot [" + bot_id + "] failed to send message: " + message.getErrorMessage());
                }
            });
    }

    /**
     * 发送私聊消息 - 异步返回
     */
    public static CompletableFuture<Message> send_private_msg(long user_id, long bot_id, String msg) {
        Logger.info("Bot [" + bot_id + "] 正在发送私聊消息到 ["+ user_id +"]: " + msg);
        
        return AdapterManager.sendPrivateMessage(user_id, msg)
            .whenComplete((message, throwable) -> {
                if (throwable != null) {
                    Logger.error("Bot [" + bot_id + "] 私聊消息发送异常: " + throwable.getMessage());
                } else if (message.isSuccess()) {
                    Logger.info("Bot [" + bot_id + "] seed a private message to ["+ user_id +"]: " + msg);
                } else {
                    Logger.warn("Bot [" + bot_id + "] failed to send private message: " + message.getErrorMessage());
                }
            });
    }

    /**
     * 发送群消息 - 异步返回
     */
    public static CompletableFuture<Message> send_group_msg(long group_id, long bot_id, String msg) {
        Logger.info("Bot [" + bot_id + "] 正在发送群消息到 ["+ group_id +"]: " + msg);
        
        return AdapterManager.sendGroupMessage(group_id, msg)
            .whenComplete((message, throwable) -> {
                if (throwable != null) {
                    Logger.error("Bot [" + bot_id + "] 群消息发送异常: " + throwable.getMessage());
                } else if (message.isSuccess()) {
                    Logger.info("Bot [" + bot_id + "] seed a group message to ["+ group_id +"]: " + msg);
                } else {
                    Logger.warn("Bot [" + bot_id + "] failed to send group message: " + message.getErrorMessage());
                }
            });
    }

    /**
     * 发送点赞（异步）
     */
    public static CompletableFuture<Boolean> send_like(long user_id, int num, long bot_id) {
        Logger.info("Bot [" + bot_id + "] send a like to ["+ user_id +"]");
        return AdapterManager.sendLike(user_id, num);
    }

    /**
     * 撤回消息（异步）
     */
    public static CompletableFuture<Boolean> delete_msg(long message_id, long bot_id) {
        Logger.info("Bot [" + bot_id + "] delete a message: " + message_id);
        return AdapterManager.deleteMessage(message_id);
    }

    /**
     * 群组踢人（异步）
     */
    public static CompletableFuture<Boolean> set_group_kick(long group_id, long user_id, long bot_id) {
        Logger.info("Bot [" + bot_id + "] kick ["+ user_id +"]");
        return AdapterManager.setGroupKick(group_id, user_id, false);
    }

    /**
     * 群组禁言（异步）
     */
    public static CompletableFuture<Boolean> set_group_ban(long group_id, long user_id, long bot_id, long time) {
        Logger.info("Bot [" + bot_id + "] ban ["+ user_id +"] for " + time + " seconds");
        return AdapterManager.setGroupBan(group_id, user_id, time);
    }
}
