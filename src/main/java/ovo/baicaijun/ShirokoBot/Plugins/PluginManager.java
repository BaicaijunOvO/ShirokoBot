package ovo.baicaijun.ShirokoBot.Plugins;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.Command.CommandManager;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件管理器
 * 参考Bukkit的PluginManager重构
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:37
 */
public class PluginManager {
    private static final Map<String, Plugin> plugins = new LinkedHashMap<>();
    private static final Map<Plugin, URLClassLoader> pluginClassLoaders = new HashMap<>();

    /**
     * 初始化插件系统
     */
    public static void init() {
        File pluginDir = new File("plugins");
        if (!pluginDir.exists()) {
            if (pluginDir.mkdirs()) {
                Logger.info("插件目录已创建: " + pluginDir.getAbsolutePath());
            } else {
                Logger.error("插件目录创建失败");
                return;
            }
        }

        File[] jarFiles = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null || jarFiles.length == 0) {
            Logger.info("未找到插件文件");
            return;
        }

        // 第一阶段：加载所有插件
        List<Plugin> loadedPlugins = new ArrayList<>();
        for (File jarFile : jarFiles) {
            Plugin plugin = loadPlugin(jarFile);
            if (plugin != null) {
                loadedPlugins.add(plugin);
            }
        }

        // 第二阶段：启用所有插件
        for (Plugin plugin : loadedPlugins) {
            enablePlugin(plugin);
        }

        Logger.info("插件初始化完成, 已加载 " + plugins.size() + " 个插件: " + plugins.keySet());
    }

    /**
     * 加载单个插件
     */
    private static Plugin loadPlugin(File jarFile) {
        try {
            // 创建独立的类加载器
            URL[] urls = {jarFile.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(urls, PluginManager.class.getClassLoader());

            // 读取plugin.yml
            try (JarFile jar = new JarFile(jarFile)) {
                JarEntry configEntry = jar.getJarEntry("plugin.yml");
                if (configEntry == null) {
                    Logger.warn("插件 " + jarFile.getName() + " 缺少配置文件 plugin.yml");
                    return null;
                }

                Yaml yaml = new Yaml();
                Map<String, Object> config;
                try (InputStream is = jar.getInputStream(configEntry)) {
                    config = yaml.load(is);
                }

                // 解析插件描述
                PluginDescriptor descriptor = parseDescriptor(config, jarFile.getName());
                if (descriptor == null) {
                    return null;
                }

                // 加载插件主类
                Class<?> clazz = classLoader.loadClass(descriptor.getMainClass());
                if (!Plugin.class.isAssignableFrom(clazz)) {
                    Logger.warn("插件类 " + descriptor.getMainClass() + " 必须继承 Plugin 类");
                    return null;
                }

                Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
                plugin.setDescriptor(descriptor);
                plugin.setClassLoader(classLoader);

                // 调用onLoad
                plugin.onLoad();

                plugins.put(descriptor.getName(), plugin);
                pluginClassLoaders.put(plugin, classLoader);

                Logger.info("成功加载插件: " + descriptor.getName() + " v" + descriptor.getVersion() + 
                           " by " + descriptor.getAuthor());
                return plugin;

            }
        } catch (Exception e) {
            Logger.error("加载插件失败: " + jarFile.getName());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析插件描述信息
     */
    private static PluginDescriptor parseDescriptor(Map<String, Object> config, String fileName) {
        String name = (String) config.get("name");
        String version = String.valueOf(config.get("version"));
        String mainClass = (String) config.get("main");

        if (name == null || version == null || mainClass == null) {
            Logger.warn("插件 " + fileName + " 配置不完整 (需要name, version, main)");
            return null;
        }

        PluginDescriptor descriptor = new PluginDescriptor(name, version, mainClass);
        descriptor.setAuthor((String) config.getOrDefault("author", "Unknown"));
        descriptor.setDescription((String) config.getOrDefault("description", ""));

        // 解析命令列表
        Object commandsObj = config.get("commands");
        if (commandsObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> commands = (List<String>) commandsObj;
            descriptor.setCommands(commands);
        }

        // 解析依赖列表
        Object dependObj = config.get("depend");
        if (dependObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> depend = (List<String>) dependObj;
            descriptor.setDepend(depend);
        }

        return descriptor;
    }

    /**
     * 启用插件
     */
    public static void enablePlugin(Plugin plugin) {
        if (plugin.isEnabled()) {
            return;
        }

        try {
            plugin.onEnable();
            plugin.setEnabled(true);
            Logger.info("插件 " + plugin.getName() + " 已启用");
        } catch (Exception e) {
            Logger.error("启用插件 " + plugin.getName() + " 时发生错误");
            e.printStackTrace();
        }
    }

    /**
     * 禁用插件
     */
    public static void disablePlugin(Plugin plugin) {
        if (!plugin.isEnabled()) {
            return;
        }

        try {
            plugin.onDisable();
            plugin.setEnabled(false);

            // 注销事件监听器和命令
            EventManager.unregisterAll(plugin);
            CommandManager.unregisterCommands(plugin);

            Logger.info("插件 " + plugin.getName() + " 已禁用");
        } catch (Exception e) {
            Logger.error("禁用插件 " + plugin.getName() + " 时发生错误");
            e.printStackTrace();
        }
    }

    /**
     * 禁用所有插件
     */
    public static void disableAllPlugins() {
        for (Plugin plugin : new ArrayList<>(plugins.values())) {
            disablePlugin(plugin);
        }

        // 关闭所有类加载器
        for (URLClassLoader classLoader : pluginClassLoaders.values()) {
            try {
                classLoader.close();
            } catch (Exception e) {
                Logger.error("关闭类加载器时发生错误: " + e.getMessage());
            }
        }

        plugins.clear();
        pluginClassLoaders.clear();
    }

    /**
     * 获取插件
     */
    public static Plugin getPlugin(String name) {
        return plugins.get(name);
    }

    /**
     * 获取所有插件
     */
    public static Collection<Plugin> getPlugins() {
        return new ArrayList<>(plugins.values());
    }

    /**
     * 检查插件是否已加载
     */
    public static boolean isPluginLoaded(String name) {
        return plugins.containsKey(name);
    }
}