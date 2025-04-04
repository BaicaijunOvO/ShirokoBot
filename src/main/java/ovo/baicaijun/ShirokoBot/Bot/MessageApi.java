package ovo.baicaijun.ShirokoBot.Bot;

import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Network.WebSocketServer;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class MessageApi {
     public static void send_private_msg(long user_id, long bot_id, String msg){
         JSONObject obj = new JSONObject();
         JSONObject params = new JSONObject();
         obj.put("action","send_private_msg");
         params.put("user_id",user_id);
         params.put("message",msg);
         obj.put("params",params);
         WebSocketServer.sendMessageToClient(obj.toString());
         Logger.info("Bot [" + bot_id + "] seed a private message to ["+ user_id +"]: " + msg);

     }
    public static void send_msg(long group_id, long user_id, long bot_id, String msg){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        String message_type = "group";
        if (group_id == 0) message_type = "private";

        obj.put("action","send_msg");
        params.put("message_type",message_type);
        params.put("group_id",group_id);
        params.put("user_id",user_id);
        params.put("message",msg);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] seed a message to ["+ group_id +"] ["+ user_id +"]: " + msg);

    }
    public static void send_group_msg(long group_id, long bot_id, String msg){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        obj.put("action","send_group_msg");
        params.put("group_id",group_id);
        params.put("message",msg);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] seed a group message to ["+ group_id +"]: " + msg);

    }
    public static void send_like (long user_id,int num, long bot_id){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        obj.put("action","send_like");
        params.put("user_id",user_id);
        params.put("times",num);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] send a like to ["+ user_id +"]");

    }
    public static void delete_msg (long message_id, long bot_id, String msg){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        obj.put("action","delete_msg");
        params.put("message_id",message_id);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] delete a message: " + msg);

    }
    public static void set_group_kick (long group_id, long user_id, long bot_id){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        obj.put("action","set_group_kick");
        params.put("group_id",group_id);
        params.put("user_id",user_id);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] kick ["+ user_id +"]");

    }

    public static void set_group_ban  (long group_id, long user_id, long bot_id, long time){
        JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        obj.put("action","set_group_ban");
        params.put("group_id",group_id);
        params.put("user_id",user_id);
        params.put("duration",time);
        obj.put("params",params);
        WebSocketServer.sendMessageToClient(obj.toString());
        Logger.info("Bot [" + bot_id + "] kick ["+ user_id +"]");

    }




}
