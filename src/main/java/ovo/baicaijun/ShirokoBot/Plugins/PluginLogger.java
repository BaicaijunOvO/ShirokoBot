package ovo.baicaijun.ShirokoBot.Plugins;

import ovo.baicaijun.ShirokoBot.Log.Logger;

/**
 * 插件日志工具类
 * 提供全局可用的插件日志记录功能
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class PluginLogger {
    private static final ThreadLocal<Plugin> currentPlugin = new ThreadLocal<>();
    
    /**
     * 设置当前线程的插件上下文
     * 由 PluginManager 在调用插件方法前自动设置
     */
    public static void setCurrentPlugin(Plugin plugin) {
        currentPlugin.set(plugin);
    }
    
    /**
     * 清除当前线程的插件上下文
     * 由 PluginManager 在调用插件方法后自动清除
     */
    public static void clearCurrentPlugin() {
        currentPlugin.remove();
    }
    
    /**
     * 获取当前线程的插件
     */
    public static Plugin getCurrentPlugin() {
        return currentPlugin.get();
    }
    
    /**
     * 记录信息日志
     * 自动添加插件名称前缀
     */
    public static void log(String message) {
        Plugin plugin = currentPlugin.get();
        if (plugin != null) {
            Logger.info("[" + plugin.getName() + "] " + message);
        } else {
            Logger.info("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 记录警告日志
     * 自动添加插件名称前缀
     */
    public static void warn(String message) {
        Plugin plugin = currentPlugin.get();
        if (plugin != null) {
            Logger.warn("[" + plugin.getName() + "] " + message);
        } else {
            Logger.warn("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 记录错误日志
     * 自动添加插件名称前缀
     */
    public static void error(String message) {
        Plugin plugin = currentPlugin.get();
        if (plugin != null) {
            Logger.error("[" + plugin.getName() + "] " + message);
        } else {
            Logger.error("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 记录调试日志
     * 自动添加插件名称前缀
     */
    public static void debug(String message) {
        Plugin plugin = currentPlugin.get();
        if (plugin != null) {
            Logger.debug("[" + plugin.getName() + "] " + message);
        } else {
            Logger.debug("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 指定插件记录信息日志
     */
    public static void log(Plugin plugin, String message) {
        if (plugin != null) {
            Logger.info("[" + plugin.getName() + "] " + message);
        } else {
            Logger.info("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 指定插件记录警告日志
     */
    public static void warn(Plugin plugin, String message) {
        if (plugin != null) {
            Logger.warn("[" + plugin.getName() + "] " + message);
        } else {
            Logger.warn("[Unknown Plugin] " + message);
        }
    }
    
    /**
     * 指定插件记录错误日志
     */
    public static void error(Plugin plugin, String message) {
        if (plugin != null) {
            Logger.error("[" + plugin.getName() + "] " + message);
        } else {
            Logger.error("[Unknown Plugin] " + message);
        }
    }
}
