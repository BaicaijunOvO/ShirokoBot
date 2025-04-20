package ovo.baicaijun.ShirokoBot.Network;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ovo.baicaijun.ShirokoBot.Config.BotConfig;
import ovo.baicaijun.ShirokoBot.Log.Logger;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午4:05
 */
public class WebSocketUtil {
    public static void init(){

        Server server = new Server(BotConfig.port);
        Logger.info("websocket监听端口: 127.0.0.1:" + BotConfig.port);

        WebSocketHandler JoinHadler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(WebSocketServer.class);
            }
        };

        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

        ContextHandler joinContext = new ContextHandler("/onebot/v11/ws");
        joinContext.setHandler(JoinHadler);

        // 将路径添加到服务器
        contextHandlerCollection.setHandlers(new ContextHandler[] {joinContext});

        // 设置服务器处理程序
        server.setHandler(contextHandlerCollection);

        // 启动服务器
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
