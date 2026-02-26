package ovo.baicaijun.ShirokoBot.Plugins.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件处理器注解
 * 参考Bukkit的@EventHandler
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    /**
     * 事件优先级
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * 是否忽略已取消的事件
     */
    boolean ignoreCancelled() default false;
}
