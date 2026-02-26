package ovo.baicaijun.ShirokoBot.Milky.parser;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Event.MessageEvent;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Milky.event.*;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;

/**
 * Milky 消息解析器
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageParser {
    
    /**
     * 解析 Milky 事件
     */
    public static void parse(String message) {
        try {
            JSONObject json = new JSONObject(message);
            Logger.debug(json.toString());
            
            long time = json.optLong("time", 0);
            long selfId = json.optLong("self_id", 0);
            String eventType = json.optString("event_type", "");
            JSONObject data = json.optJSONObject("data");
            
            if (data == null) {
                Logger.warn("Milky 事件数据为空");
                return;
            }
            
            Logger.debug("收到 Milky 事件: " + eventType);
            
            // 根据事件类型创建对应的事件对象
            MilkyEvent event = null;
            
            switch (eventType) {
                case "message_receive":
                    event = new MessageReceiveEvent(time, selfId, data);
                    // 触发消息事件处理
                    handleMessageEvent((MessageReceiveEvent) event);
                    break;
                
                case "heartbeat":
                    event = new HeartbeatEvent(time, selfId, data);
                    break;
                    
                case "friend_poke":
                    event = new FriendPokeEvent(time, selfId, data);
                    break;
                    
                case "friend_add":
                    event = new FriendAddEvent(time, selfId, data);
                    break;
                    
                case "friend_delete":
                    event = new FriendDeleteEvent(time, selfId, data);
                    break;
                    
                case "group_member_increase":
                    event = new GroupMemberIncreaseEvent(time, selfId, data);
                    break;
                    
                case "group_member_decrease":
                    event = new GroupMemberDecreaseEvent(time, selfId, data);
                    break;
                    
                case "group_admin_change":
                    event = new GroupAdminChangeEvent(time, selfId, data);
                    break;
                    
                case "group_member_ban":
                    event = new GroupMemberBanEvent(time, selfId, data);
                    break;
                    
                case "group_poke":
                    event = new GroupPokeEvent(time, selfId, data);
                    break;
                    
                default:
                    Logger.debug("未处理的 Milky 事件类型: " + eventType);
                    break;
            }
            
            // 触发事件
            if (event != null) {
                EventManager.callEvent(event);
            }
            
        } catch (Exception e) {
            Logger.error("解析 Milky 消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 处理消息事件（参照 OneBotV11 实现）
     */
    private static void handleMessageEvent(MessageReceiveEvent milkyEvent) {
        try {
            // 确定消息类型
            String messageType = milkyEvent.isGroupMessage() ? "group" : 
                                milkyEvent.isFriendMessage() ? "private" : "unknown";
            String subType = milkyEvent.getMessageScene();
            
            // 提取消息信息
            long messageId = milkyEvent.getMessageSeq();
            long groupId = milkyEvent.isGroupMessage() ? milkyEvent.getPeerId() : 0;
            long userId = milkyEvent.getSenderId();
            String rawMessage = milkyEvent.getPlainText();
            long botId = milkyEvent.getSelfId();
            
            // 触发内部事件系统（使用 MessageEvent 类）
            new MessageEvent(
                messageType,
                subType,
                messageId,
                groupId,
                userId,
                rawMessage,
                botId
            );
            
        } catch (Exception e) {
            Logger.error("处理 Milky 消息事件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
