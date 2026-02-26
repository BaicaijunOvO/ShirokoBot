package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;

/**
 * Milky 心跳事件
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class HeartbeatEvent extends MilkyEvent {
    private long interval;      // 心跳间隔（毫秒）
    private JSONObject status;  // 状态信息
    
    public HeartbeatEvent(long time, long selfId, JSONObject data) {
        super("MilkyHeartbeat", time, selfId, data);
        this.interval = data.optLong("interval", 0);
        this.status = data.optJSONObject("status");
    }
    
    public long getInterval() { return interval; }
    public JSONObject getStatus() { return status; }
    
    /**
     * 获取在线状态
     */
    public boolean isOnline() {
        if (status != null) {
            return status.optBoolean("online", false);
        }
        return false;
    }
    
    /**
     * 获取是否正常
     */
    public boolean isGood() {
        if (status != null) {
            return status.optBoolean("good", true);
        }
        return true;
    }
}
