package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

/**
 * 群管理员变动事件
 * notice.group_admin
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupAdminEvent extends NoticeEvent {
    private final String subType;
    private final long groupId;
    private final long userId;

    public GroupAdminEvent(long selfId, long time, String subType, long groupId, long userId) {
        super("group_admin", selfId, time);
        this.subType = subType;
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getSubType() {
        return subType;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isSet() {
        return "set".equals(subType);
    }

    public boolean isUnset() {
        return "unset".equals(subType);
    }
}
