package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

/**
 * 群消息撤回事件
 * notice.group_recall
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupRecallEvent extends NoticeEvent {
    private final long groupId;
    private final long operatorId;
    private final long userId;
    private final long messageId;

    public GroupRecallEvent(long selfId, long time, long groupId, long operatorId, long userId, long messageId) {
        super("group_recall", selfId, time);
        this.groupId = groupId;
        this.operatorId = operatorId;
        this.userId = userId;
        this.messageId = messageId;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public long getUserId() {
        return userId;
    }

    public long getMessageId() {
        return messageId;
    }
}
