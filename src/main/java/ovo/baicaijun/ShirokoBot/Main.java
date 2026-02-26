package ovo.baicaijun.ShirokoBot;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Config.ConfigUtil;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.PluginManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午2:23
 */
public class Main {
    public static void main(String[] args) {

        Logger.info("\n ________  ___  ___  ___  ________  ________  ___  __    ________  ________  ________  _________   \n" +
                "|\\   ____\\|\\  \\|\\  \\|\\  \\|\\   __  \\|\\   __  \\|\\  \\|\\  \\ |\\   __  \\|\\   __  \\|\\   __  \\|\\___   ___\\ \n" +
                "\\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\/  /|\\ \\  \\|\\  \\ \\  \\|\\ /\\ \\  \\|\\  \\|___ \\  \\_| \n" +
                " \\ \\_____  \\ \\   __  \\ \\  \\ \\   _  _\\ \\  \\\\\\  \\ \\   ___  \\ \\  \\\\\\  \\ \\   __  \\ \\  \\\\\\  \\   \\ \\  \\  \n" +
                "  \\|____|\\  \\ \\  \\ \\  \\ \\  \\ \\  \\\\  \\\\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\\\\\  \\ \\  \\|\\  \\ \\  \\\\\\  \\   \\ \\  \\ \n" +
                "    ____\\_\\  \\ \\__\\ \\__\\ \\__\\ \\__\\\\ _\\\\ \\_______\\ \\__\\\\ \\__\\ \\_______\\ \\_______\\ \\_______\\   \\ \\__\\\n" +
                "   |\\_________\\|__|\\|__|\\|__|\\|__|\\|__|\\|_______|\\|__| \\|__|\\|_______|\\|_______|\\|_______|    \\|__|\n" +
                "   \\|_________|                                                                                    \n" +
                "                                                                                                   ");



        String configPath = "config.json";

        BotConfig.init(configPath);
        
        // 初始化适配器管理器
        try {
            Object adapterConfigObj = ConfigUtil.getValue(configPath, "adapter");
            String adapterType;
            
            if (adapterConfigObj == null) {
                // 如果配置中没有adapter字段，使用默认值并写入配置
                Map<String, Object> adapterConfig = new HashMap<>();
                adapterConfig.put("type", "onebotv11");
                ConfigUtil.updateConfig(configPath, "adapter", adapterConfig);
                adapterType = "onebotv11";
            } else if (adapterConfigObj instanceof Map) {
                // 如果adapter是一个对象，获取type字段
                @SuppressWarnings("unchecked")
                Map<String, Object> adapterMap = (Map<String, Object>) adapterConfigObj;
                adapterType = (String) adapterMap.getOrDefault("type", "onebotv11");
            } else if (adapterConfigObj instanceof String) {
                // 如果adapter直接是字符串
                adapterType = (String) adapterConfigObj;
            } else {
                // 其他情况使用默认值
                adapterType = "onebotv11";
            }
            
            AdapterManager.initialize(adapterType, BotConfig.port);
        } catch (IOException e) {
            Logger.error("读取适配器配置失败: " + e.getMessage());
            AdapterManager.initialize("onebotv11", BotConfig.port);
        }
        
        // 初始化插件管理器
        PluginManager.init();

        // 启动适配器
        AdapterManager.start();
    }
}
