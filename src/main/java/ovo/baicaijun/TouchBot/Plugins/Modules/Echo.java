package ovo.baicaijun.TouchBot.Plugins.Modules;

import ovo.baicaijun.TouchBot.Bot.MessageApi;
import ovo.baicaijun.TouchBot.Log.Logger;
import ovo.baicaijun.TouchBot.Plugins.PluginExecutor;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:43
 */
public class Echo implements PluginExecutor {

    @Override
    public void OnMessage(String Message, long group_id, long user_id, long bot_id, long messge_id) {
//        if (group_id == 0){
//            MessageApi.send_private_msg(user_id,bot_id,Message);
//            return;
//        }
//
//        MessageApi.send_group_msg(group_id,bot_id,Message);

    }

    @Override
    public void OnCommand(String args, long group_id, long user_id, long bot_id, long messge_id) {
//        if (group_id == 0){
//            MessageApi.send_private_msg(user_id,bot_id,args);
//            return;
//        }
//
//        MessageApi.send_group_msg(group_id,bot_id,args);
        MessageApi.send_msg(group_id,user_id,bot_id,args);



    }
}
