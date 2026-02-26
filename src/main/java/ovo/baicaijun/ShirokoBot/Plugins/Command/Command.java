package ovo.baicaijun.ShirokoBot.Plugins.Command;

import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Plugins.Plugin;

/**
 * 命令类
 * 参考Bukkit的Command设计
 */
public abstract class Command {
    private final String name;
    private String description;
    private String usage;
    private Plugin plugin;

    public Command(String name) {
        this.name = name;
        this.description = "";
        this.usage = BotConfig.commandStart + name;
    }

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    /**
     * 执行命令
     * 
     * @param sender 命令发送者
     * @param args 命令参数
     * @return 是否成功执行
     */
    public abstract boolean execute(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
