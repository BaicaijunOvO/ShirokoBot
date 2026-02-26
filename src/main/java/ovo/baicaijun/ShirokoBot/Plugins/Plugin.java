package ovo.baicaijun.ShirokoBot.Plugins;

import ovo.baicaijun.ShirokoBot.Log.Logger;

/**
 * 插件基类，所有插件都应该继承此类
 * 参考Bukkit的JavaPlugin设计
 */
public abstract class Plugin {
    private PluginDescriptor descriptor;
    private boolean enabled = false;
    private ClassLoader classLoader;

    /**
     * 插件加载时调用（在onEnable之前）
     * 用于初始化插件的基础资源
     */
    public void onLoad() {
        // 默认实现为空，子类可以覆盖
    }

    /**
     * 插件启用时调用
     * 用于注册事件监听器、命令等
     */
    public abstract void onEnable();

    /**
     * 插件禁用时调用
     * 用于清理资源、保存数据等
     */
    public abstract void onDisable();

    /**
     * 获取插件描述信息
     */
    public final PluginDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * 设置插件描述信息（由PluginManager调用）
     */
    final void setDescriptor(PluginDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * 获取插件名称
     */
    public final String getName() {
        return descriptor.getName();
    }

    /**
     * 获取插件版本
     */
    public final String getVersion() {
        return descriptor.getVersion();
    }

    /**
     * 检查插件是否已启用
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置插件启用状态（由PluginManager调用）
     */
    final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取插件的类加载器
     */
    public final ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * 设置插件的类加载器（由PluginManager调用）
     */
    final void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 获取插件日志记录器
     */
    public void log(String message) {
        Logger.info("[" + getName() + "] " + message);
    }

    /**
     * 记录警告信息
     */
    public void warn(String message) {
        Logger.warn("[" + getName() + "] " + message);
    }

    /**
     * 记录错误信息
     */
    public void error(String message) {
        Logger.error("[" + getName() + "] " + message);
    }
}
