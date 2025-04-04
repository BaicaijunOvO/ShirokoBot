package ovo.baicaijun.ShirokoBot.Plugins.Modules;

import ovo.baicaijun.ShirokoBot.Bot.CommandChain;
import ovo.baicaijun.ShirokoBot.Bot.MessageApi;
import ovo.baicaijun.ShirokoBot.Bot.MessageChain;
import ovo.baicaijun.ShirokoBot.Plugins.PluginExecutor;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/4 18:14
 */public class Echo implements PluginExecutor {
    @Override
    public void OnMessage(MessageChain messageChain) {

    }

    @Override
    public void OnCommand(String[] args, CommandChain commandChain) {
        if (args.length == 0){
            return;
        }
        MessageApi.send_msg(commandChain.getGroupId(),commandChain.getUserId(),commandChain.getBotId(),args[0]);

    }

}
