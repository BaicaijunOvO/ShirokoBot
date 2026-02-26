package ovo.baicaijun.ShirokoBot;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
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

        Logger.info("""
                
                 ________  ___  ___  ___  ________  ________  ___  __    ________  ________  ________  _________  \s
                |\\   ____\\|\\  \\|\\  \\|\\  \\|\\   __  \\|\\   __  \\|\\  \\|\\  \\ |\\   __  \\|\\   __  \\|\\   __  \\|\\___   ___\\\s
                \\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\/  /|\\ \\  \\|\\  \\ \\  \\|\\ /\\ \\  \\|\\  \\|___ \\  \\_|\s
                 \\ \\_____  \\ \\   __  \\ \\  \\ \\   _  _\\ \\  \\\\\\  \\ \\   ___  \\ \\  \\\\\\  \\ \\   __  \\ \\  \\\\\\  \\   \\ \\  \\ \s
                  \\|____|\\  \\ \\  \\ \\  \\ \\  \\ \\  \\\\  \\\\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \\  \\\\\\  \\ \\  \\|\\  \\ \\  \\\\\\  \\   \\ \\  \\\s
                    ____\\_\\  \\ \\__\\ \\__\\ \\__\\ \\__\\\\ _\\\\ \\_______\\ \\__\\\\ \\__\\ \\_______\\ \\_______\\ \\_______\\   \\ \\__\\
                   |\\_________\\|__|\\|__|\\|__|\\|__|\\|__|\\|_______|\\|__| \\|__|\\|_______|\\|_______|\\|_______|    \\|__|
                   \\|_________|                                                                                   \s
                                                                                                                  \s""");



        String configPath = "config.json";
        BotConfig.init(configPath);
        
        // 初始化插件管理器
        PluginManager.init();

        // 初始化适配器（使用 BotConfig 中的配置）
        AdapterManager.initialize(BotConfig.adapterType, BotConfig.port, BotConfig.milkyUri);
        
        // 启动适配器
        AdapterManager.start();
    }
}
