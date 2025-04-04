package ovo.baicaijun.ShirokoBot.Event;

import ovo.baicaijun.ShirokoBot.Plugins.PluginExecutor;
import ovo.baicaijun.ShirokoBot.Plugins.PluginManager;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午4:26
 */
public class ComandTrigger {

    public static void OnMessage(String raw_message, long groupId, long user_id, long bot_id, long message_id) {
        if (raw_message.startsWith("/")) {
            int spaceIndex = raw_message.indexOf(" "); // 找到第一个空格的位置
            String args;
            if (spaceIndex != -1) { // 如果存在空格
                String command = raw_message.substring(0, spaceIndex); // 截取空格之前的内容
                args = raw_message.substring(spaceIndex + 1);
                for (String commands : PluginManager.plugins.values()) {
                    if (commands.equals(command.substring(1))) {
                        PluginExecutor plugin = null;
                        for (PluginExecutor key : PluginManager.plugins.keySet()) {
                            if (PluginManager.plugins.get(key).equals(command.substring(1))) {
                                plugin = key; // 找到对应的 key
                                break; // 找到后退出循环
                            }
                        }
                        plugin.OnCommand(args, groupId, user_id, bot_id, message_id);
                    }
                }
            } else {
                for (String commands : PluginManager.plugins.values()) {
                    if (commands.equals(raw_message.substring(1))) {
                        PluginExecutor plugin = null;
                        for (PluginExecutor key : PluginManager.plugins.keySet()) {
                            if (PluginManager.plugins.get(key).equals(raw_message.substring(1))) {
                                plugin = key; // 找到对应的 key
                                break; // 找到后退出循环
                            }
                        }
                        plugin.OnCommand("", groupId, user_id, bot_id, message_id);
                    }
                }

            }
        }

    }

}
