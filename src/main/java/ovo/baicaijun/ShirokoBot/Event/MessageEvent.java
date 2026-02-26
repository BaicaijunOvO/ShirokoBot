package ovo.baicaijun.ShirokoBot.Event;

import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;
import ovo.baicaijun.ShirokoBot.Plugins.Event.MessageReceiveEvent;

/**
 * 消息事件处理
 * 重构为使用新的事件系统
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 */
public class MessageEvent {

    public MessageEvent(String messageType, String subType, long messageId, long groupId, long user_id, String rawMessage, long bot_id) {
        Logger.info("Bot [" + bot_id + "] received a " + messageType + " message from [" + user_id + "] " + "group: " + groupId + ": " + rawMessage);
        
        // 触发消息接收事件
        MessageReceiveEvent event = new MessageReceiveEvent(rawMessage, groupId, user_id, bot_id, messageId);
        EventManager.callEvent(event);
        
        // 如果事件未被取消，处理命令
        if (!event.isCancelled()) {
            ComandTrigger.OnMessage(rawMessage, groupId, user_id, bot_id, messageId);
        }
    }
}
