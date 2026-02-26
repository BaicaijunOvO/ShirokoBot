package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群成员增加事件
 * notice.group_increase
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupIncreaseEvent extends NoticeEvent {
    private final String subType;
    private final long groupId;
    private final long operatorId;
    private final long userId;

    public GroupIncreaseEvent(long selfId, long time, String subType, long groupId, long operatorId, long userId) {
        super("group_increase", selfId, time);
        this.subType = subType;
        this.groupId = groupId;
        this.operatorId = operatorId;
        this.userId = userId;
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

    public boolean isApprove() {
        return "approve".equals(subType);
    }

    public boolean isInvite() {
        return "invite".equals(subType);
    }

    /**
     * 回复到群聊
     */
    public CompletableFuture<Message> reply(String message) {
        return sendGroupMessage(groupId, message);
    }
}

