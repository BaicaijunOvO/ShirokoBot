package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群成员增加事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupMemberIncreaseEvent extends MilkyEvent {
    private long groupId;       // 群ID
    private long userId;        // 用户ID
    private long operatorId;    // 操作者ID
    private String subType;     // 子类型: approve(管理员同意), invite(管理员邀请)
    
    public GroupMemberIncreaseEvent(long time, long selfId, JSONObject data) {
        super("MilkyGroupMemberIncrease", time, selfId, data);
        this.groupId = data.optLong("group_id", 0);
        this.userId = data.optLong("user_id", 0);
        this.operatorId = data.optLong("operator_id", 0);
        this.subType = data.optString("sub_type", "");
    }
    
    /**
     * 在群里发送欢迎消息
     */
    public CompletableFuture<Message> welcome(String message) {
        return sendGroupMessage(groupId, message);
    }
    
    /**
     * @新成员并发送欢迎消息
     */
    public CompletableFuture<Message> atWelcome(String message) {
        String atMessage = "[CQ:at,qq=" + userId + "] " + message;
        return sendGroupMessage(groupId, atMessage);
    }
    
    public long getGroupId() { return groupId; }
    public long getUserId() { return userId; }
    public long getOperatorId() { return operatorId; }
    public String getSubType() { return subType; }
}
