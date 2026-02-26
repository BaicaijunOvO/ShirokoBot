package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;

/**
 * 好友添加事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendAddEvent extends MilkyEvent {
    private long userId;        // 用户ID
    private String nickname;    // 昵称
    
    public FriendAddEvent(long time, long selfId, JSONObject data) {
        super("MilkyFriendAdd", time, selfId, data);
        this.userId = data.optLong("user_id", 0);
        this.nickname = data.optString("nickname", "");
    }
    
    public long getUserId() { return userId; }
    public String getNickname() { return nickname; }
}
