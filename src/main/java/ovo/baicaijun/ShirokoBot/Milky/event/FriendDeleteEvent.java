package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;

/**
 * 好友删除事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendDeleteEvent extends MilkyEvent {
    private long userId;        // 用户ID
    
    public FriendDeleteEvent(long time, long selfId, JSONObject data) {
        super("MilkyFriendDelete", time, selfId, data);
        this.userId = data.optLong("user_id", 0);
    }
    
    public long getUserId() { return userId; }
}
