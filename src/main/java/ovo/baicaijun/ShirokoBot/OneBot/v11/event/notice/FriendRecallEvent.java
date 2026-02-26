package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

/**
 * 私聊消息撤回事件
 * notice.friend_recall
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendRecallEvent extends NoticeEvent {
    private final long userId;
    private final long messageId;

    public FriendRecallEvent(long selfId, long time, long userId, long messageId) {
        super("friend_recall", selfId, time);
        this.userId = userId;
        this.messageId = messageId;
    }

    public long getUserId() {
        return userId;
    }

    public long getMessageId() {
        return messageId;
    }
}
