package ovo.baicaijun.ShirokoBot.Plugins;

import ovo.baicaijun.ShirokoBot.Bot.CommandChain;
import ovo.baicaijun.ShirokoBot.Bot.MessageChain;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:38
 */
public interface PluginExecutor {
    void OnMessage(MessageChain messageChain);
    void OnCommand(String[] args, CommandChain commandChain);
}
