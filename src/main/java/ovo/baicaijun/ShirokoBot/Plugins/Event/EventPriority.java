package ovo.baicaijun.ShirokoBot.Plugins.Event;

/**
 * 事件优先级枚举
 * 参考Bukkit的EventPriority
 */
public enum EventPriority {
    /**
     * 最低优先级，最先执行
     */
    LOWEST(0),
    
    /**
     * 低优先级
     */
    LOW(1),
    
    /**
     * 普通优先级
     */
    NORMAL(2),
    
    /**
     * 高优先级
     */
    HIGH(3),
    
    /**
     * 最高优先级
     */
    HIGHEST(4),
    
    /**
     * 监视器优先级，最后执行，用于监控事件结果
     */
    MONITOR(5);

    private final int slot;

    EventPriority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
