package ovo.baicaijun.ShirokoBot.Plugins.Command;

import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 命令发送者接口
 * 参考Bukkit的CommandSender
 */
public interface CommandSender {
    /**
     * 向发送者发送消息（异步）
     * @return CompletableFuture，可用于获取消息回执
     */
    CompletableFuture<Message> sendMessage(String message);

    /**
     * 获取发送者名称
     */
    String getName();

    /**
     * 获取用户ID
     */
    long getUserId();

    /**
     * 获取群组ID
     */
    long getGroupId();

    /**
     * 获取机器人ID
     */
    long getBotId();

    /**
     * 获取消息ID
     */
    long getMessageId();
}
