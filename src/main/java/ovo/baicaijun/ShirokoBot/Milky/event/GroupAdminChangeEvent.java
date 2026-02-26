package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 群管理员变动事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupAdminChangeEvent extends MilkyEvent {
    private long groupId;       // 群ID
    private long userId;        // 用户ID
    private String subType;     // 子类型: set(设置管理员), unset(取消管理员)
    
    public GroupAdminChangeEvent(long time, long selfId, JSONObject data) {
        super("MilkyGroupAdminChange", time, selfId, data);
        this.groupId = data.optLong("group_id", 0);
        this.userId = data.optLong("user_id", 0);
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
    public String getSubType() { return subType; }
    
    public boolean isSet() { return "set".equals(subType); }
    public boolean isUnset() { return "unset".equals(subType); }
}
