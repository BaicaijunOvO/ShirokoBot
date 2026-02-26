package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群戳一戳事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupPokeEvent extends MilkyEvent {
    private long groupId;       // 群ID
    private long userId;        // 被戳的用户ID
    private long operatorId;    // 操作者ID
    
    public GroupPokeEvent(long time, long selfId, JSONObject data) {
        super("MilkyGroupPoke", time, selfId, data);
        this.groupId = data.optLong("group_id", 0);
        this.userId = data.optLong("user_id", 0);
        this.operatorId = data.optLong("operator_id", 0);
    }
    
    /**
     * 在群里回复
     */
    public CompletableFuture<Message> reply(String message) {
        return sendGroupMessage(groupId, message);
    }
    
    /**
     * @操作者并回复
     */
    public CompletableFuture<Message> atReply(String message) {
        String atMessage = "[CQ:at,qq=" + operatorId + "] " + message;
        return sendGroupMessage(groupId, atMessage);
    }
    
    public long getGroupId() { return groupId; }
    public long getUserId() { return userId; }
    public long getOperatorId() { return operatorId; }
}
