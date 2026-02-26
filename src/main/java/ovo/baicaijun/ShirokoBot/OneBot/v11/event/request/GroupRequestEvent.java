package ovo.baicaijun.ShirokoBot.OneBot.v11.event.request;

/**
 * 群请求事件
 * request.group
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupRequestEvent extends RequestEvent {
    private final String subType;
    private final long groupId;
    private final long userId;
    private final String comment;
    private final String flag;

    public GroupRequestEvent(long selfId, long time, String subType, long groupId, long userId, String comment, String flag) {
        super("group", selfId, time);
        this.subType = subType;
        this.groupId = groupId;
        this.userId = userId;
        this.comment = comment;
        this.flag = flag;
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

    public String getComment() {
        return comment;
    }

    public String getFlag() {
        return flag;
    }

    public boolean isAdd() {
        return "add".equals(subType);
    }

    public boolean isInvite() {
        return "invite".equals(subType);
    }
}
