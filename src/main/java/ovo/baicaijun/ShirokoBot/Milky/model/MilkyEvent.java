package ovo.baicaijun.ShirokoBot.Milky.model;

import org.json.JSONObject;

/**
 * Milky 事件基类
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MilkyEvent {
    private long time;              // 事件发生时间戳
    private long selfId;            // 机器人自身ID
    private String eventType;       // 事件类型
    private JSONObject data;        // 事件数据
    
    public MilkyEvent(JSONObject json) {
        this.time = json.optLong("time", 0);
        this.selfId = json.optLong("self_id", 0);
        this.eventType = json.optString("event_type", "");
        this.data = json.optJSONObject("data");
    }
    
    public long getTime() {
        return time;
    }
    
    public long getSelfId() {
        return selfId;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public JSONObject getData() {
        return data;
    }
}
