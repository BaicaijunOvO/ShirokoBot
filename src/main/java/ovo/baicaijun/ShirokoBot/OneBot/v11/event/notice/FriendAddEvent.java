package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 好友添加事件
 * notice.friend_add
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendAddEvent extends NoticeEvent {
    private final long userId;

    public FriendAddEvent(long selfId, long time, long userId) {
        super("friend_add", selfId, time);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    /**
     * 回复给新好友
     */
    public CompletableFuture<Message> reply(String message) {
        return sendPrivateMessage(userId, message);
    }
}

