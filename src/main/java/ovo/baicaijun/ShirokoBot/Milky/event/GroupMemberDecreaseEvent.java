package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群成员减少事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupMemberDecreaseEvent extends MilkyEvent {
    private long groupId;       // 群ID
    private long userId;        // 用户ID
    private long operatorId;    // 操作者ID
    private String subType;     // 子类型: leave(主动退群), kick(被踢出), kick_me(机器人被踢)
    
    public GroupMemberDecreaseEvent(long time, long selfId, JSONObject data) {
        super("MilkyGroupMemberDecrease", time, selfId, data);
        this.groupId = data.optLong("group_id", 0);
        this.userId = data.optLong("user_id", 0);
        this.operatorId = data.optLong("operator_id", 0);
        this.subType = data.optString("sub_type", "");
    }
    
    /**
     * 在群里发送消息
     */
    public CompletableFuture<Message> sendMessage(String message) {
        return sendGroupMessage(groupId, message);
    }
    
    public long getGroupId() { return groupId; }
    public long getUserId() { return userId; }
    public long getOperatorId() { return operatorId; }
    public String getSubType() { return subType; }
    
    public boolean isLeave() { return "leave".equals(subType); }
    public boolean isKick() { return "kick".equals(subType); }
    public boolean isKickMe() { return "kick_me".equals(subType); }
}
