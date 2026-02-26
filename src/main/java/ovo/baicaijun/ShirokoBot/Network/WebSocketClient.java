package ovo.baicaijun.ShirokoBot.Network;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * WebSocket客户端
 * 用于主动连接到远程WebSocket服务器
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class WebSocketClient implements WebSocketListener {
    private Session session;
    private final String uri;
    private final Consumer<String> messageHandler;
    private org.eclipse.jetty.websocket.client.WebSocketClient client;
    private boolean autoReconnect = true;
    private int reconnectDelay = 5000; // 重连延迟（毫秒）
    
    /**
     * 创建WebSocket客户端
     * @param uri WebSocket服务器地址 (例如: ws://localhost:8080/ws)
     * @param messageHandler 消息处理器
     */
    public WebSocketClient(String uri, Consumer<String> messageHandler) {
        this.uri = uri.replace("http://","ws://") + "/event?access_token=" + BotConfig.accessToken;
        this.messageHandler = messageHandler;
    }
    
    /**
     * 连接到WebSocket服务器
     */
    public void connect() {
        try {
            client = new org.eclipse.jetty.websocket.client.WebSocketClient();
            client.start();
            
            URI serverUri = new URI(uri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            
            Logger.info("正在连接到 WebSocket 服务器: " + uri);
            Future<Session> future = client.connect(this, serverUri, request);
            
            // 等待连接建立（最多10秒）
            future.get(10, TimeUnit.SECONDS);
            
        } catch (Exception e) {
            Logger.error("WebSocket 客户端连接失败: " + e.getMessage());
            if (autoReconnect) {
                scheduleReconnect();
            }
        }
    }
    
    /**
     * 断开连接
     */
    public void disconnect() {
        autoReconnect = false;
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (client != null) {
            try {
                client.stop();
            } catch (Exception e) {
                Logger.error("停止 WebSocket 客户端失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 发送消息到服务器
     */
    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                Logger.error("发送消息失败: " + e.getMessage());
            }
        } else {
            Logger.warn("WebSocket 未连接，无法发送消息");
        }
    }
    
    /**
     * 检查连接状态
     */
    public boolean isConnected() {
        return session != null && session.isOpen();
    }
    
    /**
     * 设置是否自动重连
     */
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
    
    /**
     * 设置重连延迟（毫秒）
     */
    public void setReconnectDelay(int reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }
    
    /**
     * 安排重连
     */
    private void scheduleReconnect() {
        new Thread(() -> {
            try {
                Logger.info("将在 " + reconnectDelay + " 毫秒后尝试重连...");
                Thread.sleep(reconnectDelay);
                connect();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    // ========== WebSocketListener 接口实现 ==========
    
    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        Logger.info("WebSocket 客户端已连接: " + uri);
    }
    
    @Override
    public void onWebSocketText(String message) {
        if (messageHandler != null) {
            try {
                messageHandler.accept(message);
            } catch (Exception e) {
                Logger.error("处理 WebSocket 消息时出错: " + e.getClass().getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        // 暂不处理二进制消息
    }
    
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        String closeReason = (reason != null && !reason.isEmpty()) ? reason : "未知原因";
        Logger.info("WebSocket 客户端连接关闭: " + statusCode + " - " + closeReason);
        session = null;
        
        if (autoReconnect) {
            scheduleReconnect();
        }
    }
    
    @Override
    public void onWebSocketError(Throwable cause) {
        String errorMsg = "未知错误";
        if (cause != null) {
            if (cause.getMessage() != null && !cause.getMessage().isEmpty()) {
                errorMsg = cause.getMessage();
            } else {
                errorMsg = cause.getClass().getName();
            }
        }
        Logger.error("WebSocket 客户端错误: " + errorMsg);
        
        // 打印堆栈跟踪以便调试
        if (cause != null) {
            cause.printStackTrace();
        }
        
        if (autoReconnect && (session == null || !session.isOpen())) {
            scheduleReconnect();
        }
    }
}
