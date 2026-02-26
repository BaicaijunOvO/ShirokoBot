package ovo.baicaijun.ShirokoBot.Milky.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Milky 消息模型
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MilkyMessage {
    private String messageScene;    // 消息场景: friend, group, guild
    private long peerId;            // 对端ID
    private long messageSeq;        // 消息序列号
    private long senderId;          // 发送者ID
    private long time;              // 消息时间戳
    private List<MessageSegment> segments;  // 消息段列表
    private JSONObject friend;      // 好友信息（如果是好友消息）
    private JSONObject group;       // 群组信息（如果是群消息）
    
    public MilkyMessage(JSONObject data) {
        this.messageScene = data.optString("message_scene", "");
        this.peerId = data.optLong("peer_id", 0);
        this.messageSeq = data.optLong("message_seq", 0);
        this.senderId = data.optLong("sender_id", 0);
        this.time = data.optLong("time", 0);
        this.friend = data.optJSONObject("friend");
        this.group = data.optJSONObject("group");
        
        // 解析消息段
        this.segments = new ArrayList<>();
        JSONArray segmentsArray = data.optJSONArray("segments");
        if (segmentsArray != null) {
            for (int i = 0; i < segmentsArray.length(); i++) {
                JSONObject segmentObj = segmentsArray.optJSONObject(i);
                if (segmentObj != null) {
                    segments.add(new MessageSegment(segmentObj));
                }
            }
        }
    }
    
    /**
     * 获取纯文本消息内容
     */
    public String getPlainText() {
        StringBuilder sb = new StringBuilder();
        for (MessageSegment segment : segments) {
            if ("text".equals(segment.getType())) {
                sb.append(segment.getData().optString("text", ""));
            }
        }
        return sb.toString();
    }
    
    // Getters
    public String getMessageScene() { return messageScene; }
    public long getPeerId() { return peerId; }
    public long getMessageSeq() { return messageSeq; }
    public long getSenderId() { return senderId; }
    public long getTime() { return time; }
    public List<MessageSegment> getSegments() { return segments; }
    public JSONObject getFriend() { return friend; }
    public JSONObject getGroup() { return group; }
    
    /**
     * 消息段
     */
    public static class MessageSegment {
        private String type;        // 段类型: text, image, at, etc.
        private JSONObject data;    // 段数据
        
        public MessageSegment(JSONObject json) {
            this.type = json.optString("type", "");
            this.data = json.optJSONObject("data");
        }
        
        public String getType() { return type; }
        public JSONObject getData() { return data; }
    }
}
