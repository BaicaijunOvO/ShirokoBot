package ovo.baicaijun.ShirokoBot.OneBot.v11.event.meta;

/**
 * 生命周期事件
 * meta_event.lifecycle
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class LifecycleEvent extends MetaEvent {
    private final String subType;

    public LifecycleEvent(String subType, long selfId, long time) {
        super("lifecycle", selfId, time);
        this.subType = subType;
    }

    public String getSubType() {
        return subType;
    }

    public boolean isConnect() {
        return "connect".equals(subType);
    }

    public boolean isEnable() {
        return "enable".equals(subType);
    }

    public boolean isDisable() {
        return "disable".equals(subType);
    }
}
