package ovo.baicaijun.ShirokoBot.Milky.api;

import org.json.JSONArray;
import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Milky.util.MessageConverter;
import ovo.baicaijun.ShirokoBot.Network.NetworkUtil;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;
import ovo.baicaijun.ShirokoBot.Plugins.Event.MessageSentEvent;

import java.util.concurrent.CompletableFuture;

/**
 * Milky API 实现
 * 基于 HTTP POST 请求
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MilkyApi {
    private static String baseUrl = BotConfig.milkyUri + "/api";
    private static String cookie = "";
    
    /**
     * 设置 API 基础 URL
     */
    public static void setBaseUrl(String url) {
        baseUrl = url;
    }
    
    /**
     * 设置 Cookie
     */
    public static void setCookie(String cookieValue) {
        cookie = cookieValue;
    }
    
    /**
     * 发送 API 请求
     */
    private static CompletableFuture<JSONObject> sendRequest(String endpoint, JSONObject params) {
        CompletableFuture<JSONObject> future = new CompletableFuture<>();
        
        String url = baseUrl + endpoint;
        String jsonData = params.toString();
        
        Logger.debug("Milky API 请求: " + url + " - " + jsonData);
        
        NetworkUtil.postAsync(url, jsonData, cookie)
            .thenAccept(response -> {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    Logger.debug("Milky API 响应: " + response);
                    future.complete(responseJson);
                } catch (Exception e) {
                    Logger.error("解析 Milky API 响应失败: " + e.getMessage());
                    future.completeExceptionally(e);
                }
            })
            .exceptionally(e -> {
                Logger.error("Milky API 请求失败: " + e.getMessage());
                future.completeExceptionally(e);
                return null;
            });
        
        return future;
    }
    
    /**
     * 发送好友消息（支持 MILKY 码）
     */
    public static CompletableFuture<Message> sendFriendMessage(long userId, String message) {
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        
        // 将消息转换为消息段数组（支持 MILKY 码）
        JSONArray segments = MessageConverter.convertToSegments(message);
        params.put("message", segments);
        
        Logger.debug("发送好友消息: " + userId + " - " + message);
        
        return sendRequest("/send_private_message", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                
                if ("ok".equals(status) && retcode == 0) {
                    JSONObject data = response.optJSONObject("data");
                    if (data != null) {
                        long messageSeq = data.optLong("message_seq", 0);
                        Logger.debug("好友消息发送成功: messageSeq=" + messageSeq);
                        
                        MessageSentEvent event = new MessageSentEvent(messageSeq, message, 0, userId, 0);
                        EventManager.callEvent(event);
                        
                        return Message.success(messageSeq);
                    }
                }
                
                String errorMsg = "发送失败: status=" + status + ", retcode=" + retcode;
                Logger.warn(errorMsg);
                MessageSentEvent event = new MessageSentEvent(message, 0, userId, 0, errorMsg);
                EventManager.callEvent(event);
                
                return Message.failure(errorMsg);
            })
            .exceptionally(e -> {
                Logger.error("发送好友消息异常: " + e.getMessage());
                MessageSentEvent event = new MessageSentEvent(message, 0, userId, 0, e.getMessage());
                EventManager.callEvent(event);
                return Message.failure(e.getMessage());
            });
    }
    
    /**
     * 发送群消息（支持 MILKY 码）
     */
    public static CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        
        // 将消息转换为消息段数组（支持 MILKY 码）
        JSONArray segments = MessageConverter.convertToSegments(message);
        params.put("message", segments);
        
        Logger.debug("发送群消息: " + groupId + " - " + message);
        
        return sendRequest("/send_group_message", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                
                if ("ok".equals(status) && retcode == 0) {
                    JSONObject data = response.optJSONObject("data");
                    if (data != null) {
                        long messageSeq = data.optLong("message_seq", 0);
                        Logger.debug("群消息发送成功: messageSeq=" + messageSeq);
                        
                        MessageSentEvent event = new MessageSentEvent(messageSeq, message, groupId, 0, 0);
                        EventManager.callEvent(event);
                        
                        return Message.success(messageSeq);
                    }
                }
                
                String errorMsg = "发送失败: status=" + status + ", retcode=" + retcode;
                Logger.warn(errorMsg);
                MessageSentEvent event = new MessageSentEvent(message, groupId, 0, 0, errorMsg);
                EventManager.callEvent(event);
                
                return Message.failure(errorMsg);
            })
            .exceptionally(e -> {
                Logger.error("发送群消息异常: " + e.getMessage());
                MessageSentEvent event = new MessageSentEvent(message, groupId, 0, 0, e.getMessage());
                EventManager.callEvent(event);
                return Message.failure(e.getMessage());
            });
    }
    
    /**
     * 撤回好友消息
     */
    public static CompletableFuture<Boolean> recallFriendMessage(long userId, long messageSeq) {
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        params.put("message_seq", messageSeq);
        
        return sendRequest("/recall_private_message", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                return "ok".equals(status) && retcode == 0;
            })
            .exceptionally(e -> false);
    }
    
    /**
     * 撤回群消息
     */
    public static CompletableFuture<Boolean> recallGroupMessage(long groupId, long messageSeq) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("message_seq", messageSeq);
        
        return sendRequest("/recall_group_message", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                return "ok".equals(status) && retcode == 0;
            })
            .exceptionally(e -> false);
    }
    
    /**
     * 获取好友列表
     */
    public static CompletableFuture<JSONObject> getFriendList() {
        JSONObject params = new JSONObject();
        params.put("no_cache", false);
        return sendRequest("/get_friend_list", params);
    }
    
    /**
     * 获取群列表
     */
    public static CompletableFuture<JSONObject> getGroupList() {
        JSONObject params = new JSONObject();
        params.put("no_cache", false);
        return sendRequest("/get_group_list", params);
    }
    
    /**
     * 获取群成员列表
     */
    public static CompletableFuture<JSONObject> getGroupMemberList(long groupId) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("no_cache", false);
        return sendRequest("/get_group_member_list", params);
    }
    
    /**
     * 踢出群成员
     */
    public static CompletableFuture<Boolean> kickGroupMember(long groupId, long userId, boolean rejectAddRequest) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("reject_add_request", rejectAddRequest);
        
        return sendRequest("/kick_group_member", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                return "ok".equals(status) && retcode == 0;
            })
            .exceptionally(e -> false);
    }
    
    /**
     * 禁言群成员
     */
    public static CompletableFuture<Boolean> muteGroupMember(long groupId, long userId, long duration) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("duration", duration);
        
        return sendRequest("/set_group_member_mute", params)
            .thenApply(response -> {
                String status = response.optString("status", "");
                int retcode = response.optInt("retcode", -1);
                return "ok".equals(status) && retcode == 0;
            })
            .exceptionally(e -> false);
    }
    
    /**
     * 获取登录信息
     */
    public static CompletableFuture<JSONObject> getLoginInfo() {
        JSONObject params = new JSONObject();
        return sendRequest("/get_login_info", params);
    }
    
    /**
     * 获取群信息
     */
    public static CompletableFuture<JSONObject> getGroupInfo(long groupId) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("no_cache", false);
        return sendRequest("/get_group_info", params);
    }
    
    /**
     * 获取群成员信息
     */
    public static CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("no_cache", false);
        return sendRequest("/get_group_member_info", params);
    }
}
