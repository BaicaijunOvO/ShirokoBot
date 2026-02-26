package ovo.baicaijun.ShirokoBot.Plugins.Event;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.Plugin;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件管理器
 * 参考Bukkit的事件系统实现
 */
public class EventManager {
    private static final Map<Class<? extends Event>, List<RegisteredListener>> handlers = new ConcurrentHashMap<>();

    /**
     * 注册监听器
     */
    public static void registerEvents(Listener listener, Plugin plugin) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);
            if (annotation == null) {
                continue;
            }

            // 检查方法参数
            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1) {
                plugin.warn("事件处理方法 " + method.getName() + " 必须只有一个参数");
                continue;
            }

            if (!Event.class.isAssignableFrom(parameters[0])) {
                plugin.warn("事件处理方法 " + method.getName() + " 的参数必须是Event的子类");
                continue;
            }

            @SuppressWarnings("unchecked")
            Class<? extends Event> eventClass = (Class<? extends Event>) parameters[0];

            RegisteredListener registeredListener = new RegisteredListener(
                    listener,
                    method,
                    annotation.priority(),
                    plugin,
                    annotation.ignoreCancelled()
            );

            handlers.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(registeredListener);
            
            // 按优先级排序
            handlers.get(eventClass).sort(Comparator.comparingInt(rl -> rl.getPriority().getSlot()));

            plugin.log("注册事件监听器: " + eventClass.getSimpleName() + " -> " + method.getName());
        }
    }

    /**
     * 注销插件的所有监听器
     */
    public static void unregisterAll(Plugin plugin) {
        for (List<RegisteredListener> listeners : handlers.values()) {
            listeners.removeIf(listener -> listener.getPlugin().equals(plugin));
        }
    }

    /**
     * 触发事件
     */
    public static void callEvent(Event event) {
        List<RegisteredListener> listeners = handlers.get(event.getClass());
        if (listeners == null || listeners.isEmpty()) {
            return;
        }

        for (RegisteredListener listener : listeners) {
            if (event.isCancelled() && listener.isIgnoreCancelled()) {
                continue;
            }

            try {
                listener.callEvent(event);
            } catch (Exception e) {
                Logger.error("执行事件监听器时发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 已注册的监听器包装类
     */
    private static class RegisteredListener {
        private final Listener listener;
        private final Method method;
        private final EventPriority priority;
        private final Plugin plugin;
        private final boolean ignoreCancelled;

        public RegisteredListener(Listener listener, Method method, EventPriority priority, 
                                 Plugin plugin, boolean ignoreCancelled) {
            this.listener = listener;
            this.method = method;
            this.priority = priority;
            this.plugin = plugin;
            this.ignoreCancelled = ignoreCancelled;
            this.method.setAccessible(true);
        }

        public void callEvent(Event event) throws Exception {
            method.invoke(listener, event);
        }

        public EventPriority getPriority() {
            return priority;
        }

        public Plugin getPlugin() {
            return plugin;
        }

        public boolean isIgnoreCancelled() {
            return ignoreCancelled;
        }
    }
}
