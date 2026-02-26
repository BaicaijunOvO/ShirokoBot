package ovo.baicaijun.ShirokoBot.OneBot.v11.event.meta;

import ovo.baicaijun.ShirokoBot.OneBot.v11.event.OneBotEvent;

/**
 * 元事件基类
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public abstract class MetaEvent extends OneBotEvent {
    private final String metaEventType;

    public MetaEvent(String metaEventType, long selfId, long time) {
        super("MetaEvent", selfId, time);
        this.metaEventType = metaEventType;
    }

    public String getMetaEventType() {
        return metaEventType;
    }
}
