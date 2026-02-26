package ovo.baicaijun.ShirokoBot.Plugins.Event;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 消息接收事件
 */
public class MessageReceiveEvent extends Event {
    private final String rawMessage;
    private final long groupId;
    private final long userId;
    private final long botId;
    private final long messageId;

    public MessageReceiveEvent(String rawMessage, long groupId, long userId, long botId, long messageId) {
        super("MessageReceiveEvent");
        this.rawMessage = rawMessage;
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.messageId = messageId;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public long getBotId() {
        return botId;
    }

    public long getMessageId() {
        return messageId;
    }

    /**
     * 判断是否为群聊消息
     */
    public boolean isGroupMessage() {
        return groupId > 0;
    }

    /**
     * 判断是否为私聊消息
     */
    public boolean isPrivateMessage() {
        return groupId == 0;
    }

    /**
     * 回复消息（自动判断群聊/私聊）
     */
    public CompletableFuture<Message> reply(String message) {
        if (isGroupMessage()) {
            return AdapterManager.sendGroupMessage(groupId, message);
        } else {
            return AdapterManager.sendPrivateMessage(userId, message);
        }
    }

    /**
     * 发送私聊消息
     */
    public CompletableFuture<Message> sendPrivateMessage(long targetUserId, String message) {
        return AdapterManager.sendPrivateMessage(targetUserId, message);
    }

    /**
     * 发送群消息
     */
    public CompletableFuture<Message> sendGroupMessage(long targetGroupId, String message) {
        return AdapterManager.sendGroupMessage(targetGroupId, message);
    }

    /**
     * 发送消息到当前会话
     */
    public CompletableFuture<Message> sendMessage(String message) {
        return reply(message);
    }

    /**
     * @消息发送者
     */
    public CompletableFuture<Message> at(String message) {
        if (isGroupMessage()) {
            String atMessage = "[CQ:at,qq=" + userId + "] " + message;
            return AdapterManager.sendGroupMessage(groupId, atMessage);
        } else {
            return reply(message);
        }
    }
}

