package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

/**
 * 群禁言事件
 * notice.group_ban
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupBanEvent extends NoticeEvent {
    private final String subType;
    private final long groupId;
    private final long operatorId;
    private final long userId;
    private final long duration;

    public GroupBanEvent(long selfId, long time, String subType, long groupId, long operatorId, long userId, long duration) {
        super("group_ban", selfId, time);
        this.subType = subType;
        this.groupId = groupId;
        this.operatorId = operatorId;
        this.userId = userId;
        this.duration = duration;
    }

    public String getSubType() {
        return subType;
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

    public long getDuration() {
        return duration;
    }

    public boolean isBan() {
        return "ban".equals(subType);
    }

    public boolean isLiftBan() {
        return "lift_ban".equals(subType);
    }
}
