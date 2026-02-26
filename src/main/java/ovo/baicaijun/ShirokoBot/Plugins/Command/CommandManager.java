package ovo.baicaijun.ShirokoBot.Plugins.Command;

import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;
import ovo.baicaijun.ShirokoBot.Plugins.Plugin;
import ovo.baicaijun.ShirokoBot.Plugins.PluginLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令管理器
 * 参考Bukkit的命令系统
 */
public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();

    /**
     * 注册命令
     */
    public static void registerCommand(Plugin plugin, Command command) {
        command.setPlugin(plugin);
        commands.put(command.getName().toLowerCase(), command);
        Logger.info("[" + plugin.getName() + "] 注册命令: /" + command.getName());
    }

    /**
     * 注销插件的所有命令
     */
    public static void unregisterCommands(Plugin plugin) {
        commands.entrySet().removeIf(entry -> entry.getValue().getPlugin().equals(plugin));
    }

    /**
     * 执行命令
     */
    public static boolean executeCommand(CommandSender sender, String commandLine) {
        if (!commandLine.startsWith(BotConfig.commandStart)) {
            return false;
        }

        String[] parts = commandLine.substring(1).split(" ", 2);
        String commandName = parts[0].toLowerCase();
        String[] args = parts.length > 1 ? parts[1].split(" ") : new String[0];

        Command command = commands.get(commandName);
        if (command == null) {
            return false;
        }

        try {
            PluginLogger.setCurrentPlugin(command.getPlugin());
            try {
                return command.execute(sender, args);
            } finally {
                PluginLogger.clearCurrentPlugin();
            }
        } catch (Exception e) {
            Logger.error("执行命令时发生错误: " + e.getMessage());
            e.printStackTrace();
            sender.sendMessage("命令执行失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取已注册的命令
     */
    public static Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }

    /**
     * 获取所有已注册的命令
     */
    public static Map<String, Command> getCommands() {
        return new HashMap<>(commands);
    }
}
