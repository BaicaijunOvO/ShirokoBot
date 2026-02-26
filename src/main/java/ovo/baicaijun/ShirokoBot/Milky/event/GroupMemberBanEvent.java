package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群成员禁言事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupMemberBanEvent extends MilkyEvent {
    private long groupId;       // 群ID
    private long userId;        // 用户ID
    private long operatorId;    // 操作者ID
    private long duration;      // 禁言时长（秒）
    private String subType;     // 子类型: ban(禁言), lift_ban(解除禁言)
    
    public GroupMemberBanEvent(long time, long selfId, JSONObject data) {
        super("MilkyGroupMemberBan", time, selfId, data);
        this.groupId = data.optLong("group_id", 0);
        this.userId = data.optLong("user_id", 0);
        this.operatorId = data.optLong("operator_id", 0);
        this.duration = data.optLong("duration", 0);
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
    public long getDuration() { return duration; }
    public String getSubType() { return subType; }
    
    public boolean isBan() { return "ban".equals(subType); }
    public boolean isLiftBan() { return "lift_ban".equals(subType); }
}
