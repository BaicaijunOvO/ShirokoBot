package ovo.baicaijun.ShirokoBot.Plugins.Modules;

import ovo.baicaijun.ShirokoBot.Bot.MessageApi;
import ovo.baicaijun.ShirokoBot.Bot.MessageSegment;
import ovo.baicaijun.ShirokoBot.Plugins.PluginExecutor;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午5:45
 */
public class CQTest implements PluginExecutor {
    @Override
    public void OnMessage(String Message, long group_id, long user_id, long bot_id, long messge_id) {

    }

    @Override
    public void OnCommand(String args, long group_id, long user_id, long bot_id, long messge_id) {
        if (group_id == 0){
            MessageApi.send_private_msg(user_id,bot_id, MessageSegment.record("http://audio-cdn.api.singduck.cn/ugc/aa6d14e6e40d_1488070223.mp3?auth_key=1742378947-0-0-2fa4ee8c3ccf03546e5c1f25aca61afa"));
            return;
        }

        MessageApi.send_group_msg(group_id,bot_id,MessageSegment.at("2565144729"));


    }
}
