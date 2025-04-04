package ovo.baicaijun.ShirokoBot.Plugins.Modules;

import ovo.baicaijun.ShirokoBot.Bot.MessageApi;
import ovo.baicaijun.ShirokoBot.Bot.MessageSegment;
import ovo.baicaijun.ShirokoBot.Plugins.PluginExecutor;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/17 13:09
 */
public class Randomimg implements PluginExecutor {
    @Override
    public void OnMessage(String Message, long group_id, long user_id, long bot_id, long message_id) {

    }

    @Override
    public void OnCommand(String args, long group_id, long user_id, long bot_id, long message_id) {
        MessageApi.send_msg(group_id,user_id,bot_id, MessageSegment.image("https://api.lolimi.cn/API/dmt/api.php?type=image"));
    }
}
