package ovo.baicaijun.ShirokoBot.OneBot.v11.event.meta;

import org.json.JSONObject;

/**
 * 心跳事件
 * meta_event.heartbeat
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class HeartbeatEvent extends MetaEvent {
    private final JSONObject status;
    private final long interval;

    public HeartbeatEvent(long selfId, long time, JSONObject status, long interval) {
        super("heartbeat", selfId, time);
        this.status = status;
        this.interval = interval;
    }

    public JSONObject getStatus() {
        return status;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isOnline() {
        return status != null && status.optBoolean("online", false);
    }

    public boolean isGood() {
        return status != null && status.optBoolean("good", true);
    }
}
