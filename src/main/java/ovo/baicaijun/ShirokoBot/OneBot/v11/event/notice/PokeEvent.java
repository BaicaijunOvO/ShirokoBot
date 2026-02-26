package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 戳一戳事件
 * notice.notify.poke
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class PokeEvent extends NoticeEvent {
    private final long groupId;
    private final long userId;
    private final long targetId;

    public PokeEvent(long selfId, long time, long groupId, long userId, long targetId) {
        super("notify", selfId, time);
        this.groupId = groupId;
        this.userId = userId;
        this.targetId = targetId;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTargetId() {
        return targetId;
    }

    public boolean isPrivate() {
        return groupId == 0;
    }

    public boolean isGroup() {
        return groupId > 0;
    }

    /**
     * 回复消息（自动判断群聊/私聊）
     */
    public CompletableFuture<Message> reply(String message) {
        if (isGroup()) {
            return sendGroupMessage(groupId, message);
        } else {
            return sendPrivateMessage(userId, message);
        }
    }
}

