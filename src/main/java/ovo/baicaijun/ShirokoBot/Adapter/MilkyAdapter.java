package ovo.baicaijun.ShirokoBot.Adapter;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Milky.api.MilkyApi;
import ovo.baicaijun.ShirokoBot.Milky.parser.MessageParser;
import ovo.baicaijun.ShirokoBot.Network.WebSocketClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

/**
 * Milky 协议适配器
 * 使用 WebSocket 客户端连接到 Milky 服务器
 * 
 * @author BaicaijunOvO
 * @date 2026/02/26 21:42
 **/
public class MilkyAdapter implements BotAdapter {
    private final String serverUri;
    private WebSocketClient client;
    private Timer heartbeatTimer;
    private long heartbeatInterval = 30000; // 默认30秒心跳间隔

    /**
     * 创建 Milky 适配器
     * @param serverUri WebSocket 服务器地址 (例如: ws://localhost:3000/ws)
     */
    public MilkyAdapter(String serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public void initialize() {
        Logger.info("初始化 Milky 适配器...");
        Logger.info("服务器地址: " + serverUri);
        MilkyApi.setCookie(BotConfig.cookie);
        Logger.debug("Cookie: " + BotConfig.cookie);

        
        // 读取心跳间隔配置
        heartbeatInterval = BotConfig.getInt("milkyHeartbeatInterval", 30000);
        Logger.info("心跳间隔: " + heartbeatInterval + "ms");
    }

    @Override
    public void start() {
        Logger.info("启动 Milky 适配器，连接到: " + serverUri);
        
        // 创建 WebSocket 客户端并连接
        client = new WebSocketClient(serverUri, this::handleMessage);
        client.setAutoReconnect(true);
        client.setReconnectDelay(5000);
        client.connect();
        
        // 启动心跳
        startHeartbeat();
    }

    @Override
    public void stop() {
        Logger.info("停止 Milky 适配器");
        
        // 停止心跳
        stopHeartbeat();
        
        // 断开连接
        if (client != null) {
            client.disconnect();
        }
    }
    
    /**
     * 启动心跳定时器
     */
    private void startHeartbeat() {
        if (heartbeatTimer != null) {
            heartbeatTimer.cancel();
        }
        
        heartbeatTimer = new Timer("Milky-Heartbeat", true);
        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendHeartbeat();
            }
        }, heartbeatInterval, heartbeatInterval);
        
        Logger.info("心跳定时器已启动");
    }
    
    /**
     * 停止心跳定时器
     */
    private void stopHeartbeat() {
        if (heartbeatTimer != null) {
            heartbeatTimer.cancel();
            heartbeatTimer = null;
            Logger.info("心跳定时器已停止");
        }
    }
    
    /**
     * 发送心跳包
     */
    private void sendHeartbeat() {
        if (client != null && client.isConnected()) {
            try {
                JSONObject heartbeat = new JSONObject();
                heartbeat.put("action", "heartbeat");
                heartbeat.put("time", System.currentTimeMillis() / 1000);
                
                client.sendMessage(heartbeat.toString());
                Logger.debug("发送心跳包");
            } catch (Exception e) {
                Logger.error("发送心跳包失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 处理从服务器接收的消息
     */
    private void handleMessage(String message) {
        MessageParser.parse(message);
    }

    @Override
    public String getName() {
        return "Milky";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message) {
        if ("group".equals(messageType) && groupId != null) {
            return MilkyApi.sendGroupMessage(groupId, message);
        } else if ("private".equals(messageType) && userId != null) {
            return MilkyApi.sendFriendMessage(userId, message);
        }
        return CompletableFuture.completedFuture(Message.failure("无效的消息类型"));
    }

    @Override
    public CompletableFuture<Message> sendPrivateMessage(long userId, String message) {
        return MilkyApi.sendFriendMessage(userId, message);
    }

    @Override
    public CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        return MilkyApi.sendGroupMessage(groupId, message);
    }

    @Override
    public CompletableFuture<Boolean> deleteMessage(long groupId, long userId,long messageId) {
        if (groupId != 0) {
            return MilkyApi.recallGroupMessage(groupId,messageId);
        }else if (userId != 0) {
            return MilkyApi.recallFriendMessage(userId,messageId);
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> sendLike(long userId, int times) {
        // Milky 协议可能不支持点赞功能
        Logger.warn("Milky 协议可能不支持点赞功能");
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> setGroupKick(long groupId, long userId, boolean rejectAddRequest) {
        return MilkyApi.kickGroupMember(groupId, userId, rejectAddRequest);
    }

    @Override
    public CompletableFuture<Boolean> setGroupBan(long groupId, long userId, long duration) {
        return MilkyApi.muteGroupMember(groupId, userId, duration);
    }

    @Override
    public CompletableFuture<JSONObject> getLoginInfo() {
        return MilkyApi.getLoginInfo();
    }

    @Override
    public CompletableFuture<JSONObject> getGroupInfo(long groupId, boolean noCache) {
        return MilkyApi.getGroupInfo(groupId);
    }

    @Override
    public CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId, boolean noCache) {
        return MilkyApi.getGroupMemberInfo(groupId, userId);
    }
}
