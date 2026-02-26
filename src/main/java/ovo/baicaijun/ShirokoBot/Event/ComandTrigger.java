package ovo.baicaijun.ShirokoBot.Event;

import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Plugins.Command.BotCommandSender;
import ovo.baicaijun.ShirokoBot.Plugins.Command.CommandManager;

/**
 * 命令触发器
 * 重构为使用新的命令系统
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午4:26
 */
public class ComandTrigger {

    public static void OnMessage(String raw_message, long groupId, long user_id, long bot_id, long message_id) {
        if (raw_message.startsWith(BotConfig.commandStart)) {
            BotCommandSender sender = new BotCommandSender(groupId, user_id, bot_id, message_id);
            CommandManager.executeCommand(sender, raw_message);
        }
    }
}
