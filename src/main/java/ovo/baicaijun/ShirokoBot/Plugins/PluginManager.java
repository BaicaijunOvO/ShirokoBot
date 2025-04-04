package ovo.baicaijun.ShirokoBot.Plugins;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.Modules.Echo;

import java.util.HashMap;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:37
 */
public class PluginManager {
    public static HashMap<PluginExecutor, String> plugins = new HashMap<>();

    public static void init() {

        PluginExecutor echo = new Echo();


        plugins.put(echo,"echo");


        if (plugins.isEmpty()) {
            Logger.info("没有插件加载");
        } else {
            Logger.info("插件初始化完成,已加载插件: " + plugins.values());
        }

    }

}
