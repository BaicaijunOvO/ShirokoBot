package ovo.baicaijun.ShirokoBot.Bot;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/4 17:50
 */
public class CommandChain {
    private long groupId;
    private long userId;
    private long botId;
    private long messageId;

    public CommandChain(long groupId, long userId, long botId, long messageId) {
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.messageId = messageId;
    }

    // Getter 方法

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
}
