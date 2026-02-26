package ovo.baicaijun.ShirokoBot.Log;

import ovo.baicaijun.ShirokoBot.Config.BotConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class Logger {

//    static {
//        // 设置标准输出流的编码为 UTF-8
//        try {
//            System.setOut(new PrintStream(System.out, true, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    private static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static void debug(String msg) {
        if (BotConfig.debugMode){
            System.out.println(Colors.GREEN + "[" + getTime() + "]" + Colors.YELLOW + "[DEBUG] " + Colors.YELLOW + "| " + Colors.WHITE + msg);
        }
    }

    public static void info(String msg) {
        System.out.println(Colors.GREEN + "[" + getTime() + "]" + Colors.WHITE + "[INFO] " + Colors.YELLOW + "| " + Colors.WHITE + msg);
    }

    public static void warn(String msg) {
        System.out.println(Colors.GREEN + "[" + getTime() + "]" + Colors.YELLOW + "[WARNING] " + Colors.YELLOW + "| " + Colors.WHITE + msg);
    }

    public static void error(String msg) {
        System.out.println(Colors.GREEN + "[" + getTime() + "]" + Colors.RED + "[ERROR] " + Colors.YELLOW + "| " + Colors.WHITE + msg);
    }
}