package ovo.baicaijun.ShirokoBot.PluginTest;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:37
 */
public class PluginManager {
    public static HashMap<PluginExecutor, String> plugins = new HashMap<>();

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

        URL[] urls = new URL[jarFiles.length];
        for (int i = 0; i < jarFiles.length; i++) {
            try {
                urls[i] = jarFiles[i].toURI().toURL();
            } catch (Exception e) {
                Logger.error("无效的JAR文件路径: " + jarFiles[i].getName() + "\n" + e);
                return;
            }
        }

        try (URLClassLoader classLoader = new URLClassLoader(urls, PluginManager.class.getClassLoader())) {
            for (File jarFile : jarFiles) {
                try (JarFile jar = new JarFile(jarFile)) {
                    JarEntry configEntry = jar.getJarEntry("plugin.yml");
                    if (configEntry == null) {
                        Logger.warn("插件 " + jarFile.getName() + " 缺少配置文件 plugin.yml");
                        continue;
                    }

                    Yaml yaml = new Yaml();
                    Map<String, Object> config;
                    try (InputStream is = jar.getInputStream(configEntry)) {
                        config = yaml.load(is);
                    }

                    String className = (String) config.get("class");
                    String pluginName = (String) config.get("name");
                    if (className == null || pluginName == null) {
                        Logger.warn("插件 " + jarFile.getName() + " 配置不完整");
                        continue;
                    }

                    Class<?> clazz = classLoader.loadClass(className);
                    if (!PluginExecutor.class.isAssignableFrom(clazz)) {
                        Logger.warn("插件类 " + className + " 未实现 PluginExecutor 接口");
                        continue;
                    }

                    PluginExecutor plugin = (PluginExecutor) clazz.getDeclaredConstructor().newInstance();
                    plugins.put(plugin, pluginName);
                    Logger.info("成功加载插件: " + pluginName + " (" + className + ")");
                } catch (Exception e) {
                    Logger.error("加载插件失败: " + jarFile.getName() + "\n" + e);
                }
            }
        } catch (Exception e) {
            Logger.error("初始化插件加载器失败" + "\n" + e);
        }

        Logger.info("插件初始化完成, 已加载插件: " + plugins.values());
    }
}