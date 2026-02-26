package ovo.baicaijun.ShirokoBot.Network;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Log.Logger;

/**
 * WebSocket服务器
 * 负责WebSocket连接管理和消息转发
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午2:50
 */
public class WebSocketServer implements WebSocketListener {
    private static Session session;

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {

    }

    @Override
    public void onWebSocketText(String message) {
        // 委托给解析器处理
        AdapterManager.parse(message);
    }

    @Override
    public void onWebSocketClose(int i, String s) {
        Logger.info("WebSocket连接断开");
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        Logger.info("WebSocket已连接");
    }

    @Override
    public void onWebSocketError(Throwable throwable) {
        Logger.error("WebSocket 错误: " + throwable.getMessage());
    }

    public static void sendMessageToClient(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getRemote().sendString(message);  // 发送文本消息
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
