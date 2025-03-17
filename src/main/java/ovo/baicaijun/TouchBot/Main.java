package ovo.baicaijun.TouchBot;

import ovo.baicaijun.TouchBot.Network.WebSocketUtil;
import ovo.baicaijun.TouchBot.Plugins.PluginManager;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

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