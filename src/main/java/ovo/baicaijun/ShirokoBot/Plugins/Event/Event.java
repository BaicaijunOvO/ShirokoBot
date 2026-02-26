package ovo.baicaijun.ShirokoBot.Plugins.Event;

/**
 * 事件基类
 * 参考Bukkit的Event设计
 */
public abstract class Event {
    private final String name;
    private boolean cancelled = false;

    public Event() {
        this.name = getClass().getSimpleName();
    }

    public Event(String name) {
        this.name = name;
    }

    /**
     * 获取事件名称
     */
    public final String getEventName() {
        return name;
    }

    /**
     * 检查事件是否被取消
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * 设置事件取消状态
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
