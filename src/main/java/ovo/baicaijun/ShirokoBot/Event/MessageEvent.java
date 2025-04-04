package ovo.baicaijun.ShirokoBot.Event;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.PluginExecutor;
import ovo.baicaijun.ShirokoBot.Plugins.PluginManager;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class MessageEvent {

    public MessageEvent(String messageType, String subType, long messageId, long groupId, long user_id, String rawMessage, long bot_id) {
        Logger.info("Bot [" + bot_id + "] received a " + messageType + " message from [" + user_id + "] " + "group: " + groupId + ": " + rawMessage);
        ComandTrigger.OnMessage(rawMessage,groupId,user_id,bot_id,messageId);
        for (PluginExecutor plugin : PluginManager.plugins.keySet()) {
            plugin.OnMessage(rawMessage,groupId,user_id,bot_id,messageId);
        }
    }

}
