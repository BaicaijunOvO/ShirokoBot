package ovo.baicaijun.ShirokoBot.Config;

import ovo.baicaijun.ShirokoBot.Log.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/20 10:26
 */
public class BotConfig {
    public static Boolean debugMode = false;
    public static int port = 8080;
    public static String commandStart = "/";

    public static void init(String configPath){

        try {
            // 1. 读取配置 (如果文件不存在会返回空 Map)
            Map<String, Object> config = ConfigUtil.readConfig(configPath);

            // 2. 如果配置为空，初始化一些默认值
            if (config.isEmpty()) {
                config.put("version", "1.2");
                config.put("commandStart","/");
                config.put("debug",false);
                config.put("port",8080);
                // 写入初始配置
                ConfigUtil.writeConfig(configPath, config);
                Logger.info("初始配置文件已创建");
            }

            // 3. 读取特定值
            boolean debug = (boolean) ConfigUtil.getValue(configPath, "debug");
            Logger.info("Debug 模式: " + debug);
            debugMode = debug;

            port = (int) ConfigUtil.getValue(configPath,"port");
            commandStart = ConfigUtil.getValue(configPath, "commandStart").toString();

            // 6. 打印当前配置
            Logger.info("当前配置: " + (ConfigUtil.readConfig(configPath).toString()));

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    


}
