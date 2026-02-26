package ovo.baicaijun.ShirokoBot.Adapter;

import ovo.baicaijun.ShirokoBot.Bot.Message;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

/**
 * 适配器管理器
 * 负责管理和切换不同的协议适配器
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class AdapterManager {
    private static BotAdapter currentAdapter;
    private static String adapterType;
    
    /**
     * 初始化适配器
     * @param type 适配器类型 (onebotv11, milky)
     * @param port 端口号
     */
    public static void initialize(String type, int port) {
        adapterType = type.toLowerCase();
        
        switch (adapterType) {
            case "onebotv11":
            case "onebot":
                currentAdapter = new OneBotV11Adapter(port);
                break;
                
            case "milky":
                // TODO: 实现 Milky 适配器
                Logger.warn("Milky 适配器尚未实现，使用 OneBot v11 作为默认适配器");
                currentAdapter = new OneBotV11Adapter(port);
                break;
                
            default:
                Logger.error("未知的适配器类型: " + type + "，使用 OneBot v11 作为默认适配器");
                currentAdapter = new OneBotV11Adapter(port);
                break;
        }
        
        currentAdapter.initialize();
        Logger.info("适配器初始化完成: " + currentAdapter.getName() + " v" + currentAdapter.getVersion());
    }
    
    /**
     * 启动当前适配器
     */
    public static void start() {
        if (currentAdapter == null) {
            throw new IllegalStateException("适配器未初始化，请先调用 initialize()");
        }
        currentAdapter.start();
    }
    
    /**
     * 停止当前适配器
     */
    public static void stop() {
        if (currentAdapter != null) {
            currentAdapter.stop();
        }
    }
    
    /**
     * 获取当前适配器
     */
    public static BotAdapter getCurrentAdapter() {
        return currentAdapter;
    }
    
    /**
     * 获取适配器类型
     */
    public static String getAdapterType() {
        return adapterType;
    }
    
    // ========== 代理方法，转发到当前适配器 ==========
    
    public static CompletableFuture<Message> sendMessage(String messageType, Long groupId, Long userId, String message) {
        return currentAdapter.sendMessage(messageType, groupId, userId, message);
    }
    
    public static CompletableFuture<Message> sendPrivateMessage(long userId, String message) {
        return currentAdapter.sendPrivateMessage(userId, message);
    }
    
    public static CompletableFuture<Message> sendGroupMessage(long groupId, String message) {
        return currentAdapter.sendGroupMessage(groupId, message);
    }
    
    public static CompletableFuture<Boolean> deleteMessage(long messageId) {
        return currentAdapter.deleteMessage(messageId);
    }
    
    public static CompletableFuture<Boolean> sendLike(long userId, int times) {
        return currentAdapter.sendLike(userId, times);
    }
    
    public static CompletableFuture<Boolean> setGroupKick(long groupId, long userId, boolean rejectAddRequest) {
        return currentAdapter.setGroupKick(groupId, userId, rejectAddRequest);
    }
    
    public static CompletableFuture<Boolean> setGroupBan(long groupId, long userId, long duration) {
        return currentAdapter.setGroupBan(groupId, userId, duration);
    }
    
    public static CompletableFuture<JSONObject> getLoginInfo() {
        return currentAdapter.getLoginInfo();
    }
    
    public static CompletableFuture<JSONObject> getGroupInfo(long groupId, boolean noCache) {
        return currentAdapter.getGroupInfo(groupId, noCache);
    }
    
    public static CompletableFuture<JSONObject> getGroupMemberInfo(long groupId, long userId, boolean noCache) {
        return currentAdapter.getGroupMemberInfo(groupId, userId, noCache);
    }
}
