package ovo.baicaijun.ShirokoBot.OneBot.v11.model;

import org.json.JSONObject;

/**
 * OneBot v11 API响应模型
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class OneBotResponse {
    private final String status;
    private final int retcode;
    private final JSONObject data;
    private final String message;
    private final String echo;

    public OneBotResponse(JSONObject json) {
        this.status = json.optString("status", "");
        this.retcode = json.optInt("retcode", -1);
        this.data = json.optJSONObject("data");
        this.message = json.optString("message", "");
        this.echo = json.optString("echo", "");
    }

    public boolean isSuccess() {
        return "ok".equals(status) && retcode == 0;
    }

    public String getStatus() {
        return status;
    }

    public int getRetcode() {
        return retcode;
    }

    public JSONObject getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getEcho() {
        return echo;
    }

    public Long getMessageId() {
        if (data != null && data.has("message_id")) {
            return data.getLong("message_id");
        }
        return null;
    }
}
