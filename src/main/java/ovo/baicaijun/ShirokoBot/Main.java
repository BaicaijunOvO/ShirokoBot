package ovo.baicaijun.ShirokoBot;

import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Network.WebSocketUtil;
import ovo.baicaijun.ShirokoBot.Plugins.PluginManager;

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
        //可用PluginManager
        PluginManager.init();

        WebSocketUtil.init();

    }
}
