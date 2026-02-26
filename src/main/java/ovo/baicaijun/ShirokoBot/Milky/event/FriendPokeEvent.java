package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 好友戳一戳事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class FriendPokeEvent extends MilkyEvent {
    private long userId;        // 用户ID
    private long operatorId;    // 操作者ID
    
    public FriendPokeEvent(long time, long selfId, JSONObject data) {
        super("MilkyFriendPoke", time, selfId, data);
        this.userId = data.optLong("user_id", 0);
        this.operatorId = data.optLong("operator_id", 0);
    }
    
    /**
     * 回复戳一戳的用户
     */
    public CompletableFuture<Message> reply(String message) {
        return sendPrivateMessage(operatorId, message);
    }
    
    public long getUserId() { return userId; }
    public long getOperatorId() { return operatorId; }
}
