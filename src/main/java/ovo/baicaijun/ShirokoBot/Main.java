package ovo.baicaijun.ShirokoBot;

import ovo.baicaijun.ShirokoBot.Network.WebSocketUtil;
import ovo.baicaijun.ShirokoBot.Plugins.PluginManager;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午2:23
 */
public class Main {
    public static void main(String[] args) {
//        try {
//            // 设置标准输出流为 UTF-8 编码
//            System.setOut(new PrintStream(System.out, true, "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


        //测试PluginManager
        //PluginManager.init();

        //可用PluginManager
        PluginManager.init();

        WebSocketUtil.init();

    }
}
