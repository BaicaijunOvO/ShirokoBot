# ShirokoBot

一个基于 Java 的多协议 QQ 机器人框架，支持 OneBot V11 和 Milky 协议。

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Version](https://img.shields.io/badge/Version-1.1--SNAPSHOT-green.svg)](https://github.com/BaicaijunOvO/ShirokoBot)

## 特性

- 🔌 **多协议支持**：同时支持 OneBot V11 和 Milky 协议
- 🧩 **插件系统**：类似 Bukkit 的插件架构，支持热加载
- 📝 **事件驱动**：完整的事件系统，支持优先级和取消机制
- ⚡ **异步消息**：基于 CompletableFuture 的异步消息发送
- 🎨 **消息段支持**：支持 CQ 码和 MILKY 码格式
- 🔄 **自动重连**：WebSocket 连接断开自动重连
- 📊 **日志系统**：完善的日志记录和插件日志隔离

## 快速开始

### 环境要求

- Java 17 或更高版本
- Gradle 7.0+ (可选，项目包含 Gradle Wrapper)

### 构建项目

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

### 运行

```bash
# Windows
gradlew.bat run

# Linux/Mac
./gradlew run
```

## 插件开发

### 创建插件项目

TODO 之后再说

### 插件主类

```java
package com.example.myplugin;

import ovo.baicaijun.ShirokoBot.Plugins.Plugin;
import ovo.baicaijun.ShirokoBot.Plugins.Event.EventManager;
import ovo.baicaijun.ShirokoBot.Plugins.Command.CommandManager;

public class MyPlugin extends Plugin {
    
    @Override
    public void onLoad() {
        log("插件正在加载...");
    }
    
    @Override
    public void onEnable() {
        log("插件已启用！");
        
        // 注册事件监听器
        EventManager.registerEvents(new MyListener(this), this);
        
        // 注册命令
        CommandManager.registerCommand(this, new MyCommand());
    }
    
    @Override
    public void onDisable() {
        log("插件已禁用");
    }
}
```

### 事件监听器

```java
package com.example.myplugin;

import ovo.baicaijun.ShirokoBot.Plugins.Event.*;
import ovo.baicaijun.ShirokoBot.Plugins.Plugin;

public class MyListener implements Listener {
    
    private final Plugin plugin;
    
    public MyListener(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMessage(MessageReceiveEvent event) {
        String message = event.getRawMessage();
        
        if (message.equals("hello")) {
            event.sendMessage("Hello, World!")
                .thenAccept(receipt -> {
                    if (receipt.isSuccess()) {
                        plugin.log("消息发送成功: " + receipt.getMessageId());
                    }
                });
        }
    }
}
```

### 命令处理

```java
package com.example.myplugin;

import ovo.baicaijun.ShirokoBot.Plugins.Command.Command;
import ovo.baicaijun.ShirokoBot.Plugins.Command.CommandSender;
import ovo.baicaijun.ShirokoBot.Plugins.Plugin;

public class MyCommand extends Command {
    
    public MyCommand() {
        super("mycommand");
        setDescription("我的命令");
        setUsage("/mycommand <参数>");
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Plugin plugin = getPlugin();
        
        if (args.length == 0) {
            sender.sendMessage("用法: " + getUsage());
            return false;
        }
        
        sender.sendMessage("你输入了: " + String.join(" ", args))
            .thenAccept(receipt -> {
                if (receipt.isSuccess()) {
                    plugin.log("命令执行成功");
                }
            });
        
        return true;
    }
}
```

### 插件描述文件

在 `src/main/resources` 创建 `plugin.yml`：

```yaml
name: MyPlugin
version: 1.0.0
main: com.example.myplugin.MyPlugin
author: YourName
description: 我的第一个插件
```

### 打包插件

```bash
mvn clean package
```

将生成的 JAR 文件放入 `plugins` 目录即可。

## 消息段使用

### OneBot V11 (CQ 码)

```java
import ovo.baicaijun.ShirokoBot.OneBot.v11.MessageSegment;

// 文本
String msg = MessageSegment.text("Hello");

// @某人
String msg = MessageSegment.at(123456) + " 你好";

// 图片
String msg = MessageSegment.image("file:///path/to/image.jpg");

// 回复
String msg = MessageSegment.reply(messageId) + "回复内容";
```

### Milky 协议

```java
import ovo.baicaijun.ShirokoBot.Milky.MessageSegment;

// 文本
String msg = MessageSegment.text("Hello");

// @某人
String msg = MessageSegment.at(123456) + " 你好";

// @全体成员
String msg = MessageSegment.atAll();

// 图片
String msg = MessageSegment.image("https://example.com/image.jpg");

// 回复
String msg = MessageSegment.reply(messageSeq) + "回复内容";
```

## 支持的事件

### 通用事件

- `MessageReceiveEvent` - 消息接收事件
- `MessageSentEvent` - 消息发送回执事件

### Milky 专用事件

- `FriendPokeEvent` - 好友戳一戳
- `FriendAddEvent` - 好友添加
- `FriendDeleteEvent` - 好友删除
- `GroupAdminChangeEvent` - 群管理员变动
- `GroupMemberIncreaseEvent` - 群成员增加
- `GroupMemberDecreaseEvent` - 群成员减少
- `GroupMemberBanEvent` - 群成员禁言
- `GroupPokeEvent` - 群内戳一戳
- `HeartbeatEvent` - 心跳事件

## API 文档

### 消息发送

```java
// 通过事件发送
event.sendMessage("消息内容")
    .thenAccept(receipt -> {
        if (receipt.isSuccess()) {
            long messageId = receipt.getMessageId();
        } else {
            String error = receipt.getErrorMessage();
        }
    });

// 通过 MessageApi 发送
MessageApi.send_msg(groupId, userId, "消息内容", botId)
    .thenAccept(receipt -> {
        // 处理结果
    });
```

### 消息撤回

```java
MessageApi.delete_msg(groupId, userId, messageId, botId)
    .thenAccept(success -> {
        if (success) {
            plugin.log("撤回成功");
        }
    });
```

## 项目结构

```
TouchBot2/
├── src/main/java/ovo/baicaijun/ShirokoBot/
│   ├── Adapter/          # 协议适配器
│   │   ├── MilkyAdapter.java
│   │   └── OneBotV11Adapter.java
│   ├── Bot/              # 核心 Bot 功能
│   ├── Config/           # 配置管理
│   ├── Event/            # 事件处理
│   ├── Log/              # 日志系统
│   ├── Milky/            # Milky 协议实现
│   │   ├── api/          # API 调用
│   │   ├── event/        # 事件定义
│   │   ├── model/        # 数据模型
│   │   └── parser/       # 消息解析
│   ├── Network/          # 网络通信
│   ├── OneBot/           # OneBot V11 实现
│   └── Plugins/          # 插件系统
│       ├── Command/      # 命令系统
│       └── Event/        # 事件系统
├── plugins/              # 插件目录
└── config.json           # 配置文件
```

## 技术栈

- **Java 17+** - 编程语言
- **Jetty** - WebSocket 服务器
- **OkHttp** - HTTP 客户端
- **JSON** - 数据格式
- **Gradle** - 构建工具

## 参考项目

- [OneBot V11](https://11.onebot.dev) - 机器人协议标准
- [Milky](https://milky.ntqqrev.org) - QQ 机器人协议
- [Bukkit](https://bukkit.org) - 插件系统设计参考

## 开源协议

本项目采用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 开源协议。

## 作者

**BaicaijunOvO**

- GitHub: [@BaicaijunOvO](https://github.com/BaicaijunOvO)

## 贡献

欢迎提交 Issue 和 Pull Request！

## 更新日志

### v1.1-SNAPSHOT

- ✨ 新增 Milky 协议支持
- ✨ 完善插件系统
- ✨ 添加异步消息发送
- ✨ 支持消息发送回执
- 🐛 修复插件日志显示问题
- 🔧 优化事件系统性能

### v1.0

- 🎉 初始版本
- ✨ 支持 OneBot V11 协议
- ✨ 基础插件系统
- ✨ 命令和事件系统
