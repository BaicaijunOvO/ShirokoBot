package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

/**
 * 群成员名片更新事件
 * notice.group_card
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupCardEvent extends NoticeEvent {
    private final long groupId;
    private final long userId;
    private final String cardNew;
    private final String cardOld;

    public GroupCardEvent(long selfId, long time, long groupId, long userId, String cardNew, String cardOld) {
        super("group_card", selfId, time);
        this.groupId = groupId;
        this.userId = userId;
        this.cardNew = cardNew;
        this.cardOld = cardOld;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public String getCardNew() {
        return cardNew;
    }

    public String getCardOld() {
        return cardOld;
    }
}
