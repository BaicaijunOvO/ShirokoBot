package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONArray;
import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Milky 消息接收事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageReceiveEvent extends MilkyEvent {
    private String messageScene;    // 消息场景: friend, group, guild
    private long peerId;            // 对端ID
    private long messageSeq;        // 消息序列号
    private long senderId;          // 发送者ID
    private List<MessageSegment> segments;  // 消息段列表
    private JSONObject friend;      // 好友信息
    private JSONObject group;       // 群组信息
    
    public MessageReceiveEvent(long time, long selfId, JSONObject data) {
        super("MilkyMessageReceive", time, selfId, data);
        
        this.messageScene = data.optString("message_scene", "");
        this.peerId = data.optLong("peer_id", 0);
        this.messageSeq = data.optLong("message_seq", 0);
        this.senderId = data.optLong("sender_id", 0);
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
    
    /**
     * 回复消息
     */
    public CompletableFuture<Message> reply(String message) {
        if ("group".equals(messageScene)) {
            return sendGroupMessage(peerId, message);
        } else if ("friend".equals(messageScene)) {
            return sendPrivateMessage(peerId, message);
        }
        return CompletableFuture.completedFuture(Message.failure("未知的消息场景"));
    }
    
    /**
     * @消息发送者（仅群聊有效）
     */
    public CompletableFuture<Message> at(String message) {
        if ("group".equals(messageScene)) {
            String atMessage = "[CQ:at,qq=" + senderId + "] " + message;
            return sendGroupMessage(peerId, atMessage);
        }
        return reply(message);
    }
    
    /**
     * 撤回当前消息
     */
    public CompletableFuture<Boolean> recall() {
        if ("group".equals(messageScene)) {
            return recallGroupMessage(peerId, messageSeq);
        } else if ("friend".equals(messageScene)) {
            return recallPrivateMessage(peerId, messageSeq);
        }
        return CompletableFuture.completedFuture(false);
    }
    
    // Getters
    public String getMessageScene() { return messageScene; }
    public long getPeerId() { return peerId; }
    public long getMessageSeq() { return messageSeq; }
    public long getSenderId() { return senderId; }
    public List<MessageSegment> getSegments() { return segments; }
    public JSONObject getFriend() { return friend; }
    public JSONObject getGroup() { return group; }
    
    public boolean isGroupMessage() { return "group".equals(messageScene); }
    public boolean isFriendMessage() { return "friend".equals(messageScene); }
    public boolean isGuildMessage() { return "guild".equals(messageScene); }
    
    /**
     * 消息段
     */
    public static class MessageSegment {
        private String type;        // 段类型: text, image, at, face, etc.
        private JSONObject data;    // 段数据
        
        public MessageSegment(JSONObject json) {
            this.type = json.optString("type", "");
            this.data = json.optJSONObject("data");
        }
        
        public String getType() { return type; }
        public JSONObject getData() { return data; }
    }
}
