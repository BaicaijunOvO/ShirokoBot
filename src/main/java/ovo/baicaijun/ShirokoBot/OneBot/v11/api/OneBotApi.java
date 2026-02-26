package ovo.baicaijun.ShirokoBot.OneBot.v11.api;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Network.WebSocketServer;
import ovo.baicaijun.ShirokoBot.OneBot.v11.model.OneBotResponse;
import ovo.baicaijun.ShirokoBot.OneBot.v11.parser.MessageParser;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;
import ovo.baicaijun.ShirokoBot.Plugins.Event.MessageSentEvent;

import java.util.concurrent.CompletableFuture;

/**
 * OneBot v11 API封装
 * 提供OneBot协议的所有API调用方法
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class OneBotApi {
    private static long echoCounter = 0;
    private static final int DEFAULT_TIMEOUT = 5;      // 正常超时时间（秒）
    private static final int GRACE_PERIOD = 5;         // 宽限期时间（秒）
    
    /**
     * 设置超时时间（用于测试或特殊场景）
     */
    private static int customTimeout = DEFAULT_TIMEOUT;
    private static int customGracePeriod = GRACE_PERIOD;
    
    /**
     * 配置超时时间
     */
    public static void configureTimeout(int timeout, int gracePeriod) {
        customTimeout = timeout;
        customGracePeriod = gracePeriod;
        Logger.info("API超时配置已更新: 正常超时=" + timeout + "秒, 宽限期=" + gracePeriod + "秒");
    }

    /**
     * 生成唯一的echo标识
     */
    private static synchronized String generateEcho() {
        return "api_" + System.currentTimeMillis() + "_" + (++echoCounter);
    }

    /**
     * 异步发送API请求
     */
    private static CompletableFuture<OneBotResponse> sendRequestAsync(String action, JSONObject params) {
        JSONObject request = new JSONObject();
        String echo = generateEcho();
        
        request.put("action", action);
        request.put("echo", echo);
        if (params != null) {
            request.put("params", params);
        }
        
        CompletableFuture<OneBotResponse> future = MessageParser.registerEcho(echo);
        WebSocketServer.sendMessageToClient(request.toString());
        
        // 设置超时自动清理（使用单独的线程）
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep((customTimeout + customGracePeriod) * 1000L);
                if (!future.isDone()) {
                    MessageParser.removeEcho(echo);
                    Logger.warn("API请求超时: action=" + action + ", echo=" + echo);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        return future;
    }

    /**
     * 发送消息（返回 CompletableFuture）
     */
    public static CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message) {
        JSONObject params = new JSONObject();
        params.put("message_type", messageType);
        if (groupId != null && groupId > 0) {
            params.put("group_id", groupId);
        }
        if (userId != null) {
            params.put("user_id", userId);
        }
        params.put("message", message);
        
        long gid = (groupId != null) ? groupId : 0;
        long uid = (userId != null) ? userId : 0;
        
        CompletableFuture<OneBotResponse> responseFuture = sendRequestAsync("send_msg", params);
        
        return responseFuture.thenApply(response -> {
            if (response == null) {
                MessageSentEvent event = new MessageSentEvent(message, gid, uid, 0, "请求超时");
                EventManager.callEvent(event);
                return Message.failure("请求超时");
            }
            if (response.isSuccess()) {
                Logger.debug("消息发送成功: messageId=" + response.getMessageId());
                MessageSentEvent event = new MessageSentEvent(response.getMessageId(), message, gid, uid, 0);
                EventManager.callEvent(event);
                return Message.success(response.getMessageId());
            }
            String errorMsg = response.getMessage();
            if (errorMsg == null || errorMsg.isEmpty()) {
                errorMsg = "发送失败，状态码: " + response.getRetcode();
            }
            Logger.warn("发送消息失败: " + errorMsg);
            MessageSentEvent event = new MessageSentEvent(message, gid, uid, 0, errorMsg);
            EventManager.callEvent(event);
            return Message.failure(errorMsg);
        }).exceptionally(ex -> {
            Logger.error("消息发送异常: " + ex.getMessage());
            MessageSentEvent event = new MessageSentEvent(message, gid, uid, 0, ex.getMessage());
            EventManager.callEvent(event);
            return Message.failure(ex.getMessage());
        });
    }

    /**
     * 发送私聊消息（返回 CompletableFuture）
     */
    public static CompletableFuture<Message> sendPrivateMessage(long userId, String message) {
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        params.put("message", message);
        
        CompletableFuture<OneBotResponse> responseFuture = sendRequestAsync("send_private_msg", params);
        
        return responseFuture.thenApply(response -> {
            if (response == null) {
                MessageSentEvent event = new MessageSentEvent(message, 0, userId, 0, "请求超时");
                EventManager.callEvent(event);
                return Message.failure("请求超时");
            }
            if (response.isSuccess()) {
                Logger.debug("私聊消息发送成功: messageId=" + response.getMessageId());
                MessageSentEvent event = new MessageSentEvent(response.getMessageId(), message, 0, userId, 0);
                EventManager.callEvent(event);
                return Message.success(response.getMessageId());
            }
            String errorMsg = response.getMessage();
            if (errorMsg == null || errorMsg.isEmpty()) {
                errorMsg = "发送失败，状态码: " + response.getRetcode();
            }
            Logger.warn("发送私聊消息失败: " + errorMsg);
            MessageSentEvent event = new MessageSentEvent(message, 0, userId, 0, errorMsg);
            EventManager.callEvent(event);
            return Message.failure(errorMsg);
        }).exceptionally(ex -> {
            Logger.error("私聊消息发送异常: " + ex.getMessage());
            MessageSentEvent event = new MessageSentEvent(message, 0, userId, 0, ex.getMessage());
            EventManager.callEvent(event);
            return Message.failure(ex.getMessage());
        });
    }

    /**
     * 发送群消息（返回 CompletableFuture）
     */
    public static CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("message", message);
        
        CompletableFuture<OneBotResponse> responseFuture = sendRequestAsync("send_group_msg", params);
        
        return responseFuture.thenApply(response -> {
            if (response == null) {
                MessageSentEvent event = new MessageSentEvent(message, groupId, 0, 0, "请求超时");
                EventManager.callEvent(event);
                return Message.failure("请求超时");
            }
            if (response.isSuccess()) {
                Logger.debug("群消息发送成功: messageId=" + response.getMessageId());
                MessageSentEvent event = new MessageSentEvent(response.getMessageId(), message, groupId, 0, 0);
                EventManager.callEvent(event);
                return Message.success(response.getMessageId());
            }
            String errorMsg = response.getMessage();
            if (errorMsg == null || errorMsg.isEmpty()) {
                errorMsg = "发送失败，状态码: " + response.getRetcode();
            }
            Logger.warn("发送群消息失败: " + errorMsg);
            MessageSentEvent event = new MessageSentEvent(message, groupId, 0, 0, errorMsg);
            EventManager.callEvent(event);
            return Message.failure(errorMsg);
        }).exceptionally(ex -> {
            Logger.error("群消息发送异常: " + ex.getMessage());
            MessageSentEvent event = new MessageSentEvent(message, groupId, 0, 0, ex.getMessage());
            EventManager.callEvent(event);
            return Message.failure(ex.getMessage());
        });
    }

    /**
     * 撤回消息（异步）
     */
    public static CompletableFuture<Boolean> deleteMessage(long messageId) {
        JSONObject params = new JSONObject();
        params.put("message_id", messageId);
        
        return sendRequestAsync("delete_msg", params)
            .thenApply(response -> response != null && response.isSuccess())
            .exceptionally(ex -> {
                Logger.error("撤回消息异常: " + ex.getMessage());
                return false;
            });
    }

    /**
     * 发送点赞（异步）
     */
    public static CompletableFuture<Boolean> sendLike(long userId, int times) {
        JSONObject params = new JSONObject();
        params.put("user_id", userId);
        params.put("times", times);
        
        return sendRequestAsync("send_like", params)
            .thenApply(response -> response != null && response.isSuccess())
            .exceptionally(ex -> {
                Logger.error("发送点赞异常: " + ex.getMessage());
                return false;
            });
    }

    /**
     * 群组踢人（异步）
     */
    public static CompletableFuture<Boolean> setGroupKick(long groupId, long userId, boolean rejectAddRequest) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("reject_add_request", rejectAddRequest);
        
        return sendRequestAsync("set_group_kick", params)
            .thenApply(response -> response != null && response.isSuccess())
            .exceptionally(ex -> {
                Logger.error("群组踢人异常: " + ex.getMessage());
                return false;
            });
    }

    /**
     * 群组禁言（异步）
     */
    public static CompletableFuture<Boolean> setGroupBan(long groupId, long userId, long duration) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("duration", duration);
        
        return sendRequestAsync("set_group_ban", params)
            .thenApply(response -> response != null && response.isSuccess())
            .exceptionally(ex -> {
                Logger.error("群组禁言异常: " + ex.getMessage());
                return false;
            });
    }

    /**
     * 获取登录号信息（异步）
     */
    public static CompletableFuture<JSONObject> getLoginInfo() {
        return sendRequestAsync("get_login_info", null)
            .thenApply(response -> {
                if (response != null && response.isSuccess()) {
                    return response.getData();
                }
                return null;
            })
            .exceptionally(ex -> {
                Logger.error("获取登录号信息异常: " + ex.getMessage());
                return null;
            });
    }

    /**
     * 获取群信息（异步）
     */
    public static CompletableFuture<JSONObject> getGroupInfo(long groupId, boolean noCache) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("no_cache", noCache);
        
        return sendRequestAsync("get_group_info", params)
            .thenApply(response -> {
                if (response != null && response.isSuccess()) {
                    return response.getData();
                }
                return null;
            })
            .exceptionally(ex -> {
                Logger.error("获取群信息异常: " + ex.getMessage());
                return null;
            });
    }

    /**
     * 获取群成员信息（异步）
     */
    public static CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId, boolean noCache) {
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", userId);
        params.put("no_cache", noCache);
        
        return sendRequestAsync("get_group_member_info", params)
            .thenApply(response -> {
                if (response != null && response.isSuccess()) {
                    return response.getData();
                }
                return null;
            })
            .exceptionally(ex -> {
                Logger.error("获取群成员信息异常: " + ex.getMessage());
                return null;
            });
    }
}
