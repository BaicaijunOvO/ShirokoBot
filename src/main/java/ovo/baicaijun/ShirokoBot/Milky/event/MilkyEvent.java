package ovo.baicaijun.ShirokoBot.Milky.event;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Adapter.AdapterManager;
import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Plugins.Event.Event;

import java.util.concurrent.CompletableFuture;

/**
 * Milky 事件基类
 * 所有 Milky 事件都继承此类
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public abstract class MilkyEvent extends Event {
    protected long time;        // 事件发生时间戳
    protected long selfId;      // 机器人自身ID
    protected JSONObject data;  // 原始事件数据
    
    public MilkyEvent(String eventName, long time, long selfId, JSONObject data) {
        super(eventName);
        this.time = time;
        this.selfId = selfId;
        this.data = data;
    }
    
    public long getTime() {
        return time;
    }
    
    public long getSelfId() {
        return selfId;
    }
    
    public JSONObject getData() {
        return data;
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
     * 撤回私聊消息
     */
    public CompletableFuture<Boolean> recallPrivateMessage(long userId, long messageSeq) {
        return AdapterManager.recallPrivateMessage(userId, messageSeq);
    }
    
    /**
     * 撤回群消息
     */
    public CompletableFuture<Boolean> recallGroupMessage(long groupId, long messageSeq) {
        return AdapterManager.recallGroupMessage(groupId, messageSeq);
    }
}
