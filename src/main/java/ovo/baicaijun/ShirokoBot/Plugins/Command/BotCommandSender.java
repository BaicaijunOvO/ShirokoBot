package ovo.baicaijun.ShirokoBot.Plugins.Command;

import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Bot.MessageApi;

import java.util.concurrent.CompletableFuture;

/**
 * 机器人命令发送者实现
 */
public class BotCommandSender implements CommandSender {
    private final long groupId;
    private final long userId;
    private final long botId;
    private final long messageId;

    public BotCommandSender(long groupId, long userId, long botId, long messageId) {
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.messageId = messageId;
    }

    @Override
    public CompletableFuture<Message> sendMessage(String message) {
        return MessageApi.send_msg(groupId, userId, botId, message);
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public long getGroupId() {
        return groupId;
    }

    @Override
    public long getBotId() {
        return botId;
    }

    @Override
    public long getMessageId() {
        return messageId;
    }
}
