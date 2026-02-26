package ovo.baicaijun.ShirokoBot.Adapter;

import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Network.WebSocketUtil;
import ovo.baicaijun.ShirokoBot.OneBot.v11.api.OneBotApi;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

/**
 * OneBot v11 协议适配器
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class OneBotV11Adapter implements BotAdapter {
    private final int port;
    private Thread serverThread;
    
    public OneBotV11Adapter(int port) {
        this.port = port;
    }
    
    @Override
    public void initialize() {
        Logger.info("初始化 OneBot v11 适配器...");
        // 配置API超时时间（根据实际网络情况调整）
        OneBotApi.configureTimeout(5, 5);
    }
    
    @Override
    public void start() {
        Logger.info("启动 OneBot v11 适配器，端口: " + port);
        // 在新线程中启动WebSocket服务器，避免阻塞主线程
        serverThread = new Thread(() -> {
            WebSocketUtil.init();
        }, "WebSocket-Server-Thread");
        serverThread.start();
    }
    
    @Override
    public void stop() {
        Logger.info("停止 OneBot v11 适配器");
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
        }
    }
    
    @Override
    public String getName() {
        return "OneBot v11";
    }
    
    @Override
    public String getVersion() {
        return "11";
    }
    
    @Override
    public CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message) {
        return OneBotApi.sendMessage(messageType, groupId, userId, message);
    }
    
    @Override
    public CompletableFuture<Message> sendPrivateMessage(long userId, String message) {
        return OneBotApi.sendPrivateMessage(userId, message);
    }
    
    @Override
    public CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        return OneBotApi.sendGroupMessage(groupId, message);
    }
    
    @Override
    public CompletableFuture<Boolean> deleteMessage(long messageId) {
        return OneBotApi.deleteMessage(messageId);
    }
    
    @Override
    public CompletableFuture<Boolean> sendLike(long userId, int times) {
        return OneBotApi.sendLike(userId, times);
    }
    
    @Override
    public CompletableFuture<Boolean> setGroupKick(long groupId, long userId, boolean rejectAddRequest) {
        return OneBotApi.setGroupKick(groupId, userId, rejectAddRequest);
    }
    
    @Override
    public CompletableFuture<Boolean> setGroupBan(long groupId, long userId, long duration) {
        return OneBotApi.setGroupBan(groupId, userId, duration);
    }
    
    @Override
    public CompletableFuture<JSONObject> getLoginInfo() {
        return OneBotApi.getLoginInfo();
    }
    
    @Override
    public CompletableFuture<JSONObject> getGroupInfo(long groupId, boolean noCache) {
        return OneBotApi.getGroupInfo(groupId, noCache);
    }
    
    @Override
    public CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId, boolean noCache) {
        return OneBotApi.getGroupMemberInfo(groupId, userId, noCache);
    }
}
