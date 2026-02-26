package ovo.baicaijun.ShirokoBot.OneBot.v11.event;

import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Plugins.Event.Event;

import java.util.concurrent.CompletableFuture;

/**
 * OneBot 事件基类
 * 提供发送消息的便捷方法
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public abstract class OneBotEvent extends Event {
    private final long selfId;
    private final long time;

    public OneBotEvent(String name, long selfId, long time) {
        super(name);
        this.selfId = selfId;
        this.time = time;
    }

    public long getSelfId() {
        return selfId;
    }

    public long getTime() {
        return time;
    }

    /**
     * 发送私聊消息
     */
    public CompletableFuture<Message> sendPrivateMessage(long userId, String message) {
        return AdapterManager.sendPrivateMessage(userId, message);
    }

    /**
     * 发送群消息
     */
    public CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        return AdapterManager.sendGroupMessage(groupId, message);
    }

    /**
     * 发送消息（自动判断群聊/私聊）
     */
    public CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message) {
        return AdapterManager.sendMessage(messageType, groupId, userId, message);
    }
}
