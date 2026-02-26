package ovo.baicaijun.ShirokoBot.Plugins;

import java.util.ArrayList;
import java.util.List;

/**
 * 插件描述信息，从plugin.yml加载
 * 参考Bukkit的PluginDescriptionFile
 */
public class PluginDescriptor {
    private String name;
    private String version;
    private String mainClass;
    private String author;
    private String description;
    private List<String> commands;
    private List<String> depend;

    public PluginDescriptor(String name, String version, String mainClass) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
        this.commands = new ArrayList<>();
        this.depend = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getDepend() {
        return depend;
    }

    public void setDepend(List<String> depend) {
        this.depend = depend;
    }
}
