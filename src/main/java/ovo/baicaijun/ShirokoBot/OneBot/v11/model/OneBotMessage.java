package ovo.baicaijun.ShirokoBot.OneBot.v11.model;

import org.json.JSONObject;

/**
 * OneBot v11 消息模型
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class OneBotMessage {
    private final String messageType;
    private final String subType;
    private final long messageId;
    private final long groupId;
    private final long userId;
    private final String rawMessage;
    private final long selfId;
    private final JSONObject raw;

    public OneBotMessage(JSONObject json) {
        this.messageType = json.getString("message_type");
        this.subType = json.optString("sub_type", "normal");
        this.messageId = json.getLong("message_id");
        this.groupId = json.optLong("group_id", 0);
        this.userId = json.getLong("user_id");
        this.rawMessage = json.getString("raw_message");
        this.selfId = json.getLong("self_id");
        this.raw = json;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getSubType() {
        return subType;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public long getSelfId() {
        return selfId;
    }

    public JSONObject getRaw() {
        return raw;
    }

    public boolean isGroupMessage() {
        return "group".equals(messageType);
    }

    public boolean isPrivateMessage() {
        return "private".equals(messageType);
    }
}
