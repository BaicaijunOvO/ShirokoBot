package ovo.baicaijun.ShirokoBot.Plugins.Event;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 消息发送回执事件
 * 当消息发送成功并收到回执时触发
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageSentEvent extends Event {
    private final long messageId;
    private final String messageContent;
    private final long groupId;
    private final long userId;
    private final long botId;
    private final boolean success;
    private final String errorMessage;

    /**
     * 成功的消息发送事件
     */
    public MessageSentEvent(long messageId, String messageContent, long groupId, long userId, long botId) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.success = true;
        this.errorMessage = null;
    }

    /**
     * 失败的消息发送事件
     */
    public MessageSentEvent(String messageContent, long groupId, long userId, long botId, String errorMessage) {
        this.messageId = 0;
        this.messageContent = messageContent;
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public long getMessageId() {
        return messageId;
    }

    public String getMessageContent() {
        return messageContent;
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

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isGroupMessage() {
        return groupId > 0;
    }

    public boolean isPrivateMessage() {
        return groupId == 0;
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
     * 发送消息到原会话
     */
    public CompletableFuture<Message> sendMessage(String message) {
        if (isGroupMessage()) {
            return AdapterManager.sendGroupMessage(groupId, message);
        } else {
            return AdapterManager.sendPrivateMessage(userId, message);
        }
    }

    /**
     * 撤回已发送的消息（仅成功时可用）
     */
    public CompletableFuture<Boolean> recall() {
        if (success && messageId > 0) {
            return AdapterManager.deleteMessage(messageId);
        }
        return CompletableFuture.completedFuture(false);
    }
}

