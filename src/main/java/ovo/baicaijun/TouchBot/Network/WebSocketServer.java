package ovo.baicaijun.TouchBot.Network;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.json.JSONObject;
import ovo.baicaijun.TouchBot.Event.MessageEvent;
import ovo.baicaijun.TouchBot.Log.Logger;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午2:50
 */

public class WebSocketServer implements WebSocketListener {
    private static Session session;

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {

    }

    @Override
    public void onWebSocketText(String s) {
        try {
            JSONObject text = new JSONObject(s);
            Logger.debug(text.toString());

            // 处理消息事件
            if (text.has("message_type")) {
                MessageEvent event = null;
                String messageType = text.getString("message_type");
                long userId = text.getLong("user_id");
                long selfId = text.getLong("self_id");
                String rawMessage = text.getString("raw_message");

                if ("group".equals(messageType)) {
                    event = new MessageEvent(
                            messageType,
                            text.optString("sub_type", "normal"),
                            text.getLong("message_id"),
                            text.optLong("group_id", 0),
                            userId,
                            rawMessage,
                            selfId
                    );
                } else if ("private".equals(messageType)) {
                    event = new MessageEvent(
                            messageType,
                            text.optString("sub_type", "normal"),
                            text.getLong("message_id"),
                            text.optLong("group_id", 0),
                            userId,
                            rawMessage,
                            selfId
                    );
                }

            }


        } catch (Exception e) {
            System.err.println("消息解析失败: " + e.getMessage());
            e.printStackTrace();
        }
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
