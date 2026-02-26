package ovo.baicaijun.ShirokoBot.OneBot.v11.event.request;

/**
 * 加好友请求事件
 * request.friend
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendRequestEvent extends RequestEvent {
    private final long userId;
    private final String comment;
    private final String flag;

    public FriendRequestEvent(long selfId, long time, long userId, String comment, String flag) {
        super("friend", selfId, time);
        this.userId = userId;
        this.comment = comment;
        this.flag = flag;
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
}
