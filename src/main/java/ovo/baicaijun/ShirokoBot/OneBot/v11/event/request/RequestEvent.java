package ovo.baicaijun.ShirokoBot.OneBot.v11.event.request;

import ovo.baicaijun.ShirokoBot.OneBot.v11.event.OneBotEvent;

/**
 * 请求事件基类
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public abstract class RequestEvent extends OneBotEvent {
    private final String requestType;

    public RequestEvent(String requestType, long selfId, long time) {
        super("RequestEvent", selfId, time);
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }
}
