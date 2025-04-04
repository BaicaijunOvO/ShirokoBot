package ovo.baicaijun.ShirokoBot.Bot;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/4 17:44
 */
public class MessageChain {
    private String content;
    private long groupId;
    private long userId;
    private long botId;
    private long messageId;
    // 构造方法
    public MessageChain(String content, long groupId, long userId, long botId, long messageId) {
        this.content = content;
        this.groupId = groupId;
        this.userId = userId;
        this.botId = botId;
        this.messageId = messageId;
    }

    // Getter 方法
    public String getContent() {
        return content;
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
}
