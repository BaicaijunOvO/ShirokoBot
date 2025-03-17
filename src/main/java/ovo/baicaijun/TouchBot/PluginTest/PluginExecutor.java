package ovo.baicaijun.TouchBot.PluginTest;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午3:38
 */
public interface PluginExecutor {
    void OnMessage(String Message, long group_id, long user_id, long bot_id, long message_id);
    void OnCommand(String args, long group_id,long user_id, long bot_id, long message_id);
}
