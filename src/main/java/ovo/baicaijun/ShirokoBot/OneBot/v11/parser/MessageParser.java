package ovo.baicaijun.ShirokoBot.OneBot.v11.parser;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Event.MessageEvent;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.request.FriendRequestEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.request.GroupRequestEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.PokeEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupUploadEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupRecallEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupIncreaseEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupDecreaseEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupCardEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupBanEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.GroupAdminEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.FriendRecallEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice.FriendAddEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.meta.HeartbeatEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.event.meta.LifecycleEvent;
import ovo.baicaijun.ShirokoBot.OneBot.v11.model.OneBotMessage;
import ovo.baicaijun.ShirokoBot.OneBot.v11.model.OneBotResponse;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OneBot v11 消息解析器
 * 负责解析WebSocket接收到的OneBot协议消息
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageParser {
    private static final ConcurrentHashMap<String, CompletableFuture<OneBotResponse>> echoMap = new ConcurrentHashMap<>();

    /**
     * 注册echo，返回一个Future用于等待响应
     */
    public static CompletableFuture<OneBotResponse> registerEcho(String echo) {
        CompletableFuture<OneBotResponse> future = new CompletableFuture<>();
        echoMap.put(echo, future);
        return future;
    }

    /**
     * 移除echo映射（用于超时清理）
     */
    public static void removeEcho(String echo) {
        echoMap.remove(echo);
    }

    /**
     * 解析WebSocket接收到的消息
     */
    public static void parse(String message) {
        try {
            JSONObject json = new JSONObject(message);
            Logger.debug(json.toString());

            // 处理API响应（带echo字段的回执）
            if (json.has("echo") && !json.getString("echo").isEmpty()) {
                handleApiResponse(json);
                return;
            }

            // 处理事件消息
            if (json.has("post_type")) {
                String postType = json.getString("post_type");
                
                if ("message".equals(postType)) {
                    handleMessageEvent(json);
                } else if ("notice".equals(postType)) {
                    handleNoticeEvent(json);
                } else if ("request".equals(postType)) {
                    handleRequestEvent(json);
                } else if ("meta_event".equals(postType)) {
                    handleMetaEvent(json);
                }
                return;
            }

            // 兼容旧版本：直接包含message_type字段
            if (json.has("message_type")) {
                handleMessageEvent(json);
            }

        } catch (Exception e) {
            Logger.error("OneBot消息解析失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理API响应
     */
    private static void handleApiResponse(JSONObject json) {
        String echo = json.getString("echo");
        CompletableFuture<OneBotResponse> future = echoMap.remove(echo);
        
        if (future != null) {
            OneBotResponse response = new OneBotResponse(json);
            future.complete(response);
            Logger.debug("收到API回执: echo=" + echo + ", status=" + response.getStatus());
        } else {
            // 晚到的回执，Future已经超时或不存在
            OneBotResponse response = new OneBotResponse(json);
            Logger.warn("收到晚到或未知的API回执: echo=" + echo + ", status=" + response.getStatus() + 
                       ", messageId=" + response.getMessageId());
        }
    }

    /**
     * 处理消息事件
     */
    private static void handleMessageEvent(JSONObject json) {
        OneBotMessage message = new OneBotMessage(json);
        
        // 触发内部事件系统
        new MessageEvent(
            message.getMessageType(),
            message.getSubType(),
            message.getMessageId(),
            message.getGroupId(),
            message.getUserId(),
            message.getRawMessage(),
            message.getSelfId()
        );
    }

    /**
     * 处理通知事件
     */
    private static void handleNoticeEvent(JSONObject json) {
        String noticeType = json.getString("notice_type");
        long selfId = json.optLong("self_id", 0);
        long time = json.optLong("time", System.currentTimeMillis() / 1000);
        
        switch (noticeType) {
            case "group_upload":
                EventManager.callEvent(new GroupUploadEvent(
                    selfId, time,
                    json.optLong("group_id", 0),
                    json.optLong("user_id", 0),
                    json.optJSONObject("file")
                ));
                break;
                
            case "group_admin":
                EventManager.callEvent(new GroupAdminEvent(
                    selfId, time,
                    json.optString("sub_type", ""),
                    json.optLong("group_id", 0),
                    json.optLong("user_id", 0)
                ));
                break;
                
            case "group_decrease":
                EventManager.callEvent(new GroupDecreaseEvent(
                    selfId, time,
                    json.optString("sub_type", ""),
                    json.optLong("group_id", 0),
                    json.optLong("operator_id", 0),
                    json.optLong("user_id", 0)
                ));
                break;
                
            case "group_increase":
                EventManager.callEvent(new GroupIncreaseEvent(
                    selfId, time,
                    json.optString("sub_type", ""),
                    json.optLong("group_id", 0),
                    json.optLong("operator_id", 0),
                    json.optLong("user_id", 0)
                ));
                break;
                
            case "group_ban":
                EventManager.callEvent(new GroupBanEvent(
                    selfId, time,
                    json.optString("sub_type", ""),
                    json.optLong("group_id", 0),
                    json.optLong("operator_id", 0),
                    json.optLong("user_id", 0),
                    json.optLong("duration", 0)
                ));
                break;
                
            case "friend_add":
                EventManager.callEvent(new FriendAddEvent(
                    selfId, time,
                    json.optLong("user_id", 0)
                ));
                break;
                
            case "group_recall":
                EventManager.callEvent(new GroupRecallEvent(
                    selfId, time,
                    json.optLong("group_id", 0),
                    json.optLong("operator_id", 0),
                    json.optLong("user_id", 0),
                    json.optLong("message_id", 0)
                ));
                break;
                
            case "friend_recall":
                EventManager.callEvent(new FriendRecallEvent(
                    selfId, time,
                    json.optLong("user_id", 0),
                    json.optLong("message_id", 0)
                ));
                break;
                
            case "notify":
                String subType = json.optString("sub_type", "");
                if ("poke".equals(subType)) {
                    EventManager.callEvent(new PokeEvent(
                        selfId, time,
                        json.optLong("group_id", 0),
                        json.optLong("user_id", 0),
                        json.optLong("target_id", 0)
                    ));
                }
                break;
                
            case "group_card":
                EventManager.callEvent(new GroupCardEvent(
                    selfId, time,
                    json.optLong("group_id", 0),
                    json.optLong("user_id", 0),
                    json.optString("card_new", ""),
                    json.optString("card_old", "")
                ));
                break;
                
            default:
                Logger.debug("未处理的通知事件: " + noticeType);
                break;
        }
    }

    /**
     * 处理请求事件
     */
    private static void handleRequestEvent(JSONObject json) {
        String requestType = json.getString("request_type");
        long selfId = json.optLong("self_id", 0);
        long time = json.optLong("time", System.currentTimeMillis() / 1000);
        
        switch (requestType) {
            case "friend":
                EventManager.callEvent(new FriendRequestEvent(
                    selfId, time,
                    json.optLong("user_id", 0),
                    json.optString("comment", ""),
                    json.optString("flag", "")
                ));
                break;
                
            case "group":
                EventManager.callEvent(new GroupRequestEvent(
                    selfId, time,
                    json.optString("sub_type", ""),
                    json.optLong("group_id", 0),
                    json.optLong("user_id", 0),
                    json.optString("comment", ""),
                    json.optString("flag", "")
                ));
                break;
                
            default:
                Logger.debug("未处理的请求事件: " + requestType);
                break;
        }
    }

    /**
     * 处理元事件
     */
    private static void handleMetaEvent(JSONObject json) {
        String metaEventType = json.getString("meta_event_type");
        long selfId = json.optLong("self_id", 0);
        long time = json.optLong("time", System.currentTimeMillis() / 1000);
        
        switch (metaEventType) {
            case "lifecycle":
                EventManager.callEvent(new LifecycleEvent(
                    json.optString("sub_type", ""),
                    selfId, time
                ));
                break;
                
            case "heartbeat":
                EventManager.callEvent(new HeartbeatEvent(
                    selfId, time,
                    json.optJSONObject("status"),
                    json.optLong("interval", 0)
                ));
                break;
                
            default:
                Logger.debug("未处理的元事件: " + metaEventType);
                break;
        }
    }
}
